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


</body>
</html>