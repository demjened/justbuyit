<#import "/spring.ftl" as spring />
<#assign subtitle = "Welcome">

<!DOCTYPE html>
<html>
<#include "include-header.ftl">
<body>
  <div class="container">
    <div class="jumbotron">
      <h2>JusyBuyIt! - ${subtitle?upper_case}</h2>
      <h3 style="color: #337ab7">Hello ${name}!</h3>
      <p>Thank you for subscribing to JustBuyIt! We're happy you're here.</p>
      <p>However, this application does absolutely nothing. Its sole purpose is to demonstrate its integration capabilities with AppDirect.</p>
      <p>
        <a class="btn btn-lg btn-success" href="https://www.appdirect.com" role="button">Go to AppDirect</a>
        <a class="btn btn-lg btn-primary" href="<@spring.url '/logout'/>" role="button">Log out</a>
      </p>
    </div>
  </div>
</body>
</html>