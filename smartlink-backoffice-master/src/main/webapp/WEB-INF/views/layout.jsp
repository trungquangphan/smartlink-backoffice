<!DOCTYPE html>
<html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <title layout:title-pattern="$DECORATOR_TITLE - $CONTENT_TITLE">Smartlink Backoffice</title>

    <p layout:include="header :: head">Custom header here</p>
</head>
<body>
<header>
    <p layout:include="header :: header">Custom header here</p>
</header>
<div id="wrap">
    <section layout:fragment="content">
        <p>Page content goes here</p>
    </section>
</div>
<footer class="footer navbar-fixed-bottom">
    <p layout:include="footer :: footer">Custom footer here</p>
</footer>
</body>
</html>