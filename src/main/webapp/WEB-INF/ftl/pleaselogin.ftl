<#import "/spring.ftl" as spring />
<#assign subtitle = "Please log in">

<!DOCTYPE html>
<html>
<#include "include-header.ftl">
<body>
  <div class="container">
    <div class="jumbotron">
      <h2>JusyBuyIt! - ${subtitle?upper_case}</h2>
      <p>Welcome to JustBuyIt!</p>
      <p>You are not logged in. Please log in via AppDirect.</span></p>
      <p>
        <a class="btn btn-lg btn-success" href="<@spring.url '/login?openid_url=https://www.appdirect.com/openid/id'/>" role="button">Log in</a>
        <a class="btn btn-lg btn-info" href="<@spring.url '/status'/>" role="button">Registration status</a>
      </p>
    </div>
  </div>
</body>
</html>