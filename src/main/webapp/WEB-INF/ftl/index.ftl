<!DOCTYPE html>
<html>
<head>
  <title>JustBuyIt!</title>
<head>
<body>
  <b>Companies</b>
  <table border="1">
    <#list companies as company>
    <tr>
      <td>${company.name!""}</td>
      <td>${company.uuid!""}</td>
    </tr>
    </#list>
  </table>
  
  <b>Users</b>
  <table border="1">
    <#list users as user>
    <tr>
      <td>${user.email!""}</td>
      <td>${user.firstName!""}</td>
      <td>${user.lastName!""}</td>
      <td>${user.openId!""}</td>
      <td>${user.uuid!""}</td>
    </tr>
    </#list>
  </table>  
</body>
</html>