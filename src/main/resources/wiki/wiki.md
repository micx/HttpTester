# 自动化测试文档
## 应用安装
* 项目打包生成war包
* 将war包放入tomcat 启动tomcat即可

## 用例格式
* 一个excel文件表示一个模块
    * 每个excel文件的一个sheet表示一个接口
        * 每一个sheet的格式如下表
            * 必填字段:ID/Domain/Path/AssertType/ExpectResult
            * 其他都是选填字段

|`ID`|`Domain`|`Path`|`AssertType`|`ExpectResult`|`HttpMethod`|$param1|$param2|cookies|repeat|threadNum|timeout|
|--|--|--|--|--|--|--|--|--|--|
|`[必填]`场景|`[必填]`域名|`[必填]`路径|`[必填]`断言：contains/equals/script|`[必填]`期望值|`[必填]`Http请求方式:POST/GET(默认POST)|参数1|参数2|cookies:json格式|场景重复请求多少次|场景并发数|请求的超时时间(ms)|
|场景一|appmock.dp|/mw/request_builder|contains|code:200|GET|p1|p2|cookies...|1|1|500|
|场景二|appmock.dp|/mw/request_builder|contains|code:200|POST|p1|p2|cookies...|2|5|500|
|场景三|appmock.dp|/mw/request_builder|contains|code:200|GET|p1|p2|cookies...|10|10|500|

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