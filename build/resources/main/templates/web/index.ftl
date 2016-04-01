<!DOCTYPE HTML>
<html>
<head>
    <title>Spring Freemarker</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/css/bootstrap.min.css" />
    <!--<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet"/>-->
</head>
<body>
<div class="container">
    <div class="control-group">
        <h2 class="muted">Employee</h2>
        <form name="employee" action="addEmployee" method="post">
            <div class="control-group">
                <label class="control-label" for="employeeId">Employee Id</label>
            </div>
            <div class="controls">
                <input type="text" name="employeeId">
            </div>
            <div class="control-group">
                <label class="control-label" for="employeeName">Employee Name</label>
            </div>
            <div class="controls">
                <input type="text" name="employeeName">
            </div>
            <div class="control-group">
                <label class="control-label" for="employeePhone">Employee Phone </label>
            </div>

            <div class="controls">
                <input type="text" name="employeePhone">
            </div>
            <div class="controls">
                <input type="submit" class="btn btn-primary">
            </div>
        </form>
    </div>
    <div>
    <#if employees?size != 0 >
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Employee Id</th>
                <th>Employee Name</th>
                <th>Employee Phone</th>
            </tr>
            </thead>
            <tbody>
                <#list employees as employee>
                <tr>
                    <td>${employee.employeeId}</td>
                    <td>${employee.employeeName}</td>
                    <td>${employee.employeePhone}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </#if>
    </div>
</div>
</body>
</html>