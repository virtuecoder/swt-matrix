package usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import pl.netanel.util.Files;

public class Refactoring {
  
  
  @Test public void test() {
    String s1 = 
      "\n" +
      "  * @param axisY vertical axis for the matrix\n" +
      "  * @param axisX horizontal axis for the matrix";
    
    String s2 = 
      "\n" +
      "  * @param axisX horizontal axis for the matrix\n" +
      "  * @param axisY vertical axis for the matrix";
    
    Pattern p = Pattern.compile(
      "\\n(\\s*\\* @param (\\w+)0 .*)" 
        +
      "\\n(\\s*\\* @param (\\2)1 .*)", 
      Pattern.DOTALL);

    assertTrue(p.matcher(s1).find());
    
    Matcher matcher = p.matcher(s1);
    String replaced = "";
    if (matcher.find()) {
      replaced = matcher.replaceFirst("\n$3\n$1");
    }
    
    assertEquals(s2, replaced);
  }
  
  String switchParams(String content) {
    Pattern p = Pattern.compile(
      "\\n(\\s*\\* @param (\\w+)0 .*)" 
        +
      "\\n(\\s*\\* @param (\\2)1 .*)", 
      Pattern.DOTALL);

    String replaced = "";
    Matcher matcher = p.matcher(content);
    if (matcher.find()) {
      replaced = matcher.replaceAll("\n$3\n$1");
    }
    return replaced;
  }

  String snippetTitle(String content) {
    Pattern p = Pattern.compile( 
      "shell.setText\\(\"(.*)\"\\)" );

    Matcher matcher = p.matcher(content);
    return matcher.find() ? matcher.group(1).trim() : null;
  }
  
  String text2filename(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == ' ') {
        while(i++ < s.length() && s.charAt(i) == ' ');
        if (i > s.length()) break;
        char ch = s.charAt(i);
        sb.append(Character.isLetterOrDigit(ch) ? Character.toUpperCase(ch) : "_");
      }
      else {
        char ch = s.charAt(i);
        sb.append(Character.isLetterOrDigit(ch) ? ch : "_");
      }
    }
    return sb.toString();
  }

  
  ArrayList<File> getJavaFiles(String[] roots) {
    ArrayList<File> list = new ArrayList<File>();
    for (String root: roots) {
      appendJavaFilesRecursively(new File(root), list);
    }
    return list;
  }
  
  void appendJavaFilesRecursively(File file, List<File> list) {
    if (file.isDirectory()) {
      for (File file2: file.listFiles()) {
        appendJavaFilesRecursively(file2, list);
      }
    }
    else if (file.getName().endsWith(".java")) {
      list.add(file);
    }
  }
  
  
  public static void main(String[] args) throws IOException {
    Refactoring r = new Refactoring();
    
    ArrayList<File> javaFiles = r.getJavaFiles(new String[] {"src_demo"});
    for (File file: javaFiles) {
      String content = Files.readText(file);
      String code = file.getName().substring(8, 12);
      String snippetTitle = r.snippetTitle(content);
      if (snippetTitle == null) continue;
      System.out.println(snippetTitle);
      
      String newFileName = "_" + code + "_" + r.text2filename(snippetTitle);
      int indexOf = content.lastIndexOf("}");
      if (indexOf != -1) {
        content = content.substring(0, indexOf-1).
          replaceAll("shell.setText\\(\"(.*)\"\\)", "shell.setText(title)").
          replace(file.getName().substring(0, 12), newFileName) +
          "\n\n\t// Meta data" +
          "\n\tstatic final String title = \"" + snippetTitle + "\";" +
          "\n\tstatic final String instructions = \"\";" +
          "\n\tstatic final String code = \"" + code + "\";" +
          "\n}";
      }
      
      Files.write(new File("src_demo/pl/netanel/swt/matrix/snippets", newFileName + ".java"),
        content);
      
      Files.deleteGracefully(file);
//      System.out.println(content);
//      break;
    }
  }

}
