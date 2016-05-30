<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#--<meta http-equiv="refresh" content="20">-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://v3.bootcss.com/favicon.ico">

    <title>Http Tester</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- Custom styles for this template -->
    <link href="http://v3.bootcss.com/examples/dashboard/dashboard.css" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="http://v3.bootcss.com/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="http://cdn.bootcss.com/markdown.js/0.5.0/markdown.min.js"></script>
    <script src="/js/md.js" type="text/javascript"></script>
    <script src="/js/strapdown.js"></script>


</head>

<body >
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    上传测试用例(xlsx)
                </h4>
            </div>
            <div class="modal-body">

                <div class="form-group">
                    <form action="upload.do" method="post" enctype="multipart/form-data">
                        <input type="file" id="exampleInputFile" name="file" />
                        <br>
                        <input type="submit" class="btn btn-default" value="Submit" />
                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <#--<button type="button" class="btn btn-primary">-->
                    <#--提交更改-->
                <#--</button>-->
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/index">Tester</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="wiki">Wiki</a></li>
                <#--<li><a href="#">Settings</a></li>-->
                <#--<li><a href="#">Profile</a></li>-->
                <#--<li><a href="#">Help</a></li>-->
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">

            <ul class="nav nav-sidebar">
                <li >
                    <a  data-toggle="modal" data-target="#myModal" href="#">
                        上传测试脚本
                    </a>
                </li>
            </ul>


            <ul class="nav nav-sidebar">
            <#if selectedId == -2>
                <li class="active"><a href="wiki">全部脚本<span class="sr-only">(current)</span></a></li>
            </#if>
            </ul>

            <ul class="nav nav-sidebar">
                <#if selectedId == -1>
                    <li class="active"><a href="all">全部脚本<span class="sr-only">(current)</span></a></li>
                <#else >
                    <li><a href="all">全部脚本</a></li>
                </#if>
            </ul>
            <ul class="nav nav-sidebar">

                <#list navList as navgation>
                    <#if navgation.selected == 1>
                        <li class="active"><a href="index?id=${navgation.id}">${navgation.name}<span class="sr-only">(current)</span></a></li>
                    <#else >
                        <li><a href="index?id=${navgation.id}">${navgation.name}</a></li>
                    </#if>
                </#list>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
