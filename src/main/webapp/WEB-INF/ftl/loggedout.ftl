<#import "/spring.ftl" as spring />
<#assign subtitle = "Logout successful">

<!DOCTYPE html>
<html>
<#include "include-header.ftl">
<body>
  <div class="container">
    <div class="jumbotron">
      <h2>JusyBuyIt! - ${subtitle?upper_case}</h2>
      <p>You have successfully logged out of JustBuyIt!</p>
      <p>
        <a class="btn btn-lg btn-info" href="<@spring.url '/status'/>" role="button">Registration status</a>
        <a class="btn btn-lg btn-primary" href="https://www.appdirect.com" role="button">Go to AppDirect</a>
      </p>
    </div>
  </div>
</body>
</html>