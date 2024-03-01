package vn.edu.iuh.fit;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.util.Optional;

public class CommonOperations {
    public void sample(File projectDir) {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new PackageChecker().checkPackage(path);
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        String clsName = n.getNameAsString();
                        char c = clsName.charAt(0);
                        if (c >= 'a' && c <= 'z') {
                            System.out.println("2. Invalid class name: " + clsName
                                    + " Position [" + n.getBegin() + "], " + n.getEnd());
                        }

                        Optional<Comment> oCmt = n.getComment();

                        if (oCmt.isEmpty()) {
                            System.out.println("3. Missing comment for class: " + clsName
                                    + " Position [" + n.getBegin() + "], " + n.getEnd());
                        } else {
                            Comment cmt = oCmt.get();
                            String content = cmt.getContent();


                            if (content.contains("@Author")) {
                                System.out.println("3. Missing @Author for class: " + clsName
                                        + " Position [" + n.getBegin() + "], " + n.getEnd());
                            }
                            if (content.contains("@Create-Date")) {
                                System.out.println("3. Missing @Date for class: " + clsName
                                        + " Position [" + n.getBegin() + "], " + n.getEnd());
                            }
                        }
                    }

                    @Override
                    public void visit(MethodDeclaration n, Object arg) {
                        super.visit(n, arg);
                        String methodName = n.getNameAsString();

                        char c = methodName.charAt(0);
                        if (!(c >= 'a' && c <= 'z')) {
                            System.out.println("6. Invalid method name: " + methodName
                                    + " Position [" + n.getBegin() + "], " + n.getEnd());
                        }

                        if (isSpecialMethod(n)) {
                            Optional<Comment> oCmt = n.getComment();
                            if (oCmt.isEmpty()) {
                                System.out.println("7. Missing comment for method: " + methodName
                                        + " Position [" + n.getBegin() + "], " + n.getEnd());
                            }
                        }
                    }

                    @Override
                    public void visit(FieldDeclaration n, Object arg) {
                        super.visit(n, arg);
                        String field = n.getVariables().get(0).getNameAsString();
                        char c = field.charAt(0);
                        if (!(c >= 'a' && c <= 'z') && !n.isFinal()) {
                            System.out.println("4. Invalid field name: " + field
                                    + " Position [" + n.getBegin() + "], " + n.getEnd());
                        }

                        if(n.isFinal() && n.getVariables().size() == 1 && n.getParentNode().get() instanceof ClassOrInterfaceDeclaration){
                            if (!field.equals(field.toUpperCase())) {
                                System.out.println("5. Constants should be in uppercase: " + field
                                        + " Position [" + n.getBegin() + "], " + n.getEnd());
                            }
                        }
                    }

                    private boolean isSpecialMethod(MethodDeclaration methodDeclaration) {
                        String methodName = methodDeclaration.getNameAsString();
                        return methodName.equals("hashCode") || methodName.equals("equals")
                                || methodName.equals("toString");
                    }
                }.visit(StaticJavaParser.parse(file), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).explore(projectDir);
    }

    public static void main(String[] args) {
        File projectDir = new File("src/main/java");
        new CommonOperations().sample(projectDir);
    }
}
