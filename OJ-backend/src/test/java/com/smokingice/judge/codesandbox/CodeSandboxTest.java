package com.smokingice.judge.codesandbox;


import cn.hutool.json.JSONUtil;
import com.smokingice.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import com.smokingice.judge.codesandbox.model.ExecuteCodeRequest;
import com.smokingice.judge.codesandbox.model.ExecuteCodeResponse;
import com.smokingice.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class CodeSandboxTest {
    @Value("${codesandbox.type:remote}")
    private String type;

    @Test
    void executeCodeByProxy(){
        CodeSandbox codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> list  = Arrays.asList("1 2", "3 4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(list)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByType(){
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.valueOf(args[0]);\n" +
                "        int b = Integer.valueOf(args[1]);\n" +
                "        System.out.println(\"相加结果是： \" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> list  = Arrays.asList("1 2", "3 4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(list)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(JSONUtil.toJsonStr(executeCodeResponse));
        Assertions.assertNotNull(executeCodeResponse);
    }

    public static void main(String[] args) {


    }
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 1) return new TreeNode(preorder[0]);
        if(inorder.length == 1) return new TreeNode(inorder[0]);
        if(preorder.length == 0) return null;
        if(inorder.length == 0) return null;

        TreeNode root = new TreeNode(preorder[0]);

        int len = 0;
        while(preorder[0] != inorder[len] && len < preorder.length-1) {
            len++;
        }
        int[] leftPre;
        int[] leftIn;
        int[] rightPre;
        int[] rightIn;

        //如果左树长度等于preorder的长度
        if(len == preorder.length-1) {
            leftPre = Arrays.copyOfRange(preorder, 1, 1);
            leftIn = Arrays.copyOfRange(inorder, 0, len);
            rightPre = new int[]{};
            rightIn = new int[]{};
        }else{
            leftPre = Arrays.copyOfRange(preorder, 1, len);
            leftIn = Arrays.copyOfRange(inorder, 0, len);
            rightPre = Arrays.copyOfRange(preorder, len+1, preorder.length-1);
            rightIn = Arrays.copyOfRange(inorder, len+1, inorder.length-1);
        }
        root.left = buildTree(leftPre, leftIn);
        root.right = buildTree(rightPre, rightIn);

        return root;
    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}