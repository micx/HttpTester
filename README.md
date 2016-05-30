# HttpTester
http api tester
## 应用安装
* 项目打包生成war包
* 将war包放入tomcat 启动tomcat即可

## 用例格式
* 一个excel文件表示一个模块
    * 每个excel文件的一个sheet表示一个接口
        * 每一个sheet的格式如下表
            * 必填字段:ID/Domain/Path/AssertType/ExpectResult
            * 其他都是选填字段

|`ID`|`Domain`|`Path`|`AssertType`|`ExpectResult`|`HttpMethod`|$param1|$param2|cookies|repeat|threadNum|timeout|skip|


* 断言
    * contains: 返回结果包含`ExpectResult`指定字段即返回true
    * equals: 返回结果等于`ExpectResult`指定字段即返回true
    * script
    
    
script 格式如下
```
{
    "assertGroups":[
        {
            "groupName":"group - 1",
            "assertLogic":"AND",
            "statements":[
                {
                    "type":"CONTAINS",
                    "content":"上海市长宁区安化路492号D2食堂"
                },
                {
                    "type":"CONTAINS",
                    "content":"愚园路1240号"
                }
            ]
        },
        {
            "groupName":"group - 2",
            "assertLogic":"OR",
            "statements":[
                {
                    "type":"CONTAINS",
                    "content":"上海"
                },
                {
                    "type":"CONTAINS",
                    "content":"world"
                }
            ]
        }
    ]
}
```