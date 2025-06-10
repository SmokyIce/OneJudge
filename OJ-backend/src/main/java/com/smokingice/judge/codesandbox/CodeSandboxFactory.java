package com.smokingice.judge.codesandbox;

import com.smokingice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.smokingice.judge.codesandbox.impl.RemoteCodeSandBox;
import com.smokingice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（格努字符串参数创建指定的代码沙箱实例）
 * @author SmokingIce
 *
 */
public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandBox();
            case "example":
                return new ExampleCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new RemoteCodeSandBox();
        }
    }
}
