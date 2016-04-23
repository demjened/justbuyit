<#assign subtitle = "Status">

<!DOCTYPE html>
<html>
<#include "include-header.ftl">
<body>
  <div class="container">
    <div class="jumbotron">
      <h2>JusyBuyIt! - ${subtitle?upper_case}</h2>
      
      <div class="panel panel-default">
        <div class="panel-heading">Registered users</div>

        <table class="table table-striped">
          <thead>
            <tr>
              <th>Company</th>
              <th>Edition</th>
              <th>Status</th>
              <th>User</th>
            </tr>
          </thead>
          <tbody>
            <#list companies as company>
              <#list company.users as user>
              <tr>
                <td>
                  ${company.name!""}<br>
                  <span style="font-size: x-small; color: gray">${company.uuid!""}</span><br>
                </td>
                <td>
                  ${company.subscriptionEditionCode!""}
                </td>
                <td>
                  ${company.subscriptionStatus!""}
                </td>
                <td>
                  ${user.firstName!"Unknown"} ${user.lastName!"Unknown"}<br>
                  <span style="font-size: x-small; color: gray">${user.uuid!""}</span><br>
                  <span style="font-size: x-small; color: gray">${user.email!""}</span>
                <td>
              </tr>
              </#list>
            </#list>
          </tbody>
        </table>
      </div>

    </div>
  </div>
  
</body>
</html>