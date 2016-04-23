<#assign subtitle = "Error">

<!DOCTYPE html>
<html>
<#include "include-header.ftl">
<body>
  <div class="container">
    <div class="jumbotron">
      <h2 style="color: #ce4844">JusyBuyIt! - ${subtitle?upper_case}</h2>
      <p>Something went wrong. Please contact your administrator.</p>
      <p>Exception: <span style="color: gray">${exception!"Unknown error"}</span></p>
      <p>
        <a class="btn btn-lg btn-primary" href="https://www.appdirect.com" role="button">Go to AppDirect</a>
      </p>
    </div>
  </div>
</body>
</html>