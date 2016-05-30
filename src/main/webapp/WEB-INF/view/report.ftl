<#include "header.ftl"/>
<head>
    <title>原生json格式化及高亮</title>
    <meta name="description" content="json,格式化,高亮">
    <meta name="keywords" content="json,格式化,高亮">
    <script src="/js/c.js" type="text/javascript"></script>
    <script src="/js/test.js" type="text/javascript"></script>
    <link href="/css/s.css" type="text/css" rel="stylesheet"></link>

</head>
<body onLoad="javascript:Process();">
<div class="HeadersRow">
<#--<h3 id="HeaderSubTitle">Response:</h3>-->
    <textarea id="RawJson" style="display:none"> ${actualJson} </textarea>
    <textarea id="RawJson1" style="display:none"> ${exceptJson} </textarea>
</div>
<div id="ControlsRow">
    <code>
        <span id="memStatus">init monitor ...</span>
        <br>
        <span id="threadStatus"></span>
        <br>
        <br>
    </code>
    <#if actualJson??>

        <ul class="pagination">
            <li><a href="#">&laquo;</a></li>
            <#list pageList as page>
                <#if page.selected == 1 >
                    <li class="active"><a href="${page.url}">${page.value}</a></li>
                <#else>
                    <li><a href="${page.url}">${page.value}</a></li>
                </#if>
            </#list>
            <li><a href="#">&raquo;</a></li>
        </ul>
    <#--<input type="Button" value="格式化" onclick="Process()"/>-->
        <span id="TabSizeHolder" width="20px">
          缩进量
          <select id="TabSize" onchange="TabSizeChanged()">
              <option value="1">1</option>
              <option value="2" selected="true">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
          </select>
        </span>
        <label for="QuoteKeys">
            <input type="checkbox" id="QuoteKeys" onclick="QuoteKeysClicked()" checked="true"/>
            引号
        </label>&nbsp;


        <a href="javascript:void(0);" onclick="SelectAllClicked()">全选</a>
        &nbsp;
        <span id="CollapsibleViewHolder">
            <label for="CollapsibleView">
                <input type="checkbox" id="CollapsibleView" onclick="CollapsibleViewClicked()" checked="true"/>
                显示控制
            </label>
        </span>
        <span id="CollapsibleViewDetail">
          <a href="javascript:void(0);" onclick="ExpandAllClicked()">展开</a>
          <a href="javascript:void(0);" onclick="CollapseAllClicked()">叠起</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(3)">2级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(4)">3级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(5)">4级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(6)">5级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(7)">6级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(8)">7级</a>
          <a href="javascript:void(0);" onclick="CollapseLevel(9)">8级</a>
        </span>


    <#else>

            <div class="progress" id="process">
                <div id="progress-bar" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percent}"
                     aria-valuemin="0" aria-valuemax="100" style="width: ${percent}%">
                    <span class="sr-only">${percent}% Complete</span>
                </div>
            <span id="processMsg"></span>
            </div>
        <#if percent < 100>
            <#if selectedId == -1>
                <button id="runBtn" type="button" class="btn btn-info"  key="taskKey=${key}" path="/all" content="runTask=true">运行中...</button>
            <#else>
                <button id="runBtn" type="button" class="btn btn-info"  key="taskKey=${key}" path="/index" content="id=${selectedId}&runTask=true">运行中...</button>
            </#if>
        <#else>
            <#if selectedId == -1>
                <button id="runBtn" type="button" class="btn btn-info" onclick="runTask()" key="taskKey=${key}" path="/all" content="runTask=true">运行</button>
            <#else>
                <button id="runBtn" type="button" class="btn btn-info" onclick="runTask()" key="taskKey=${key}" path="/index" content="id=${selectedId}&runTask=true">运行</button>
            </#if>
        </#if>
        <br>
        <table width="300" class="table table-bordered">
            <tr class="info">
                <td>模块</td>
                <td>接口</td>
                <td>通过率</td>
                <td>场景</td>
                <td>是否通过</td>
                <td>性能测试</td>
                <td>错误信息</td>
            </tr>
            <#list trList as tr>
                <tr>
                    <#list tr.tdList as td>
                        <#if td.rowSpan != 0 >
                            <td rowspan="${td.rowSpan}" class="${td.style}">  ${td.value}</td>
                        <#else>
                            <td class="${td.style}">  ${td.value}</td>
                        </#if>
                    </#list>
                </tr>
            </#list>
        </table>

    </#if>
</div>
<#if actualJson>
    <div class="row">
        <div id="Canvas1" class="col-md-4 Canvas"></div>
        <div id="Canvas" class="col-md-8 Canvas"></div>
    </div>
    <script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
    <script type="text/javascript" src="/js/m.js"></script>
</#if>
</body>

<#include "footer.ftl"/>
