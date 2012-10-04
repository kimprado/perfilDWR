<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type='text/javascript' src='<%= request.getContextPath() %>/dwr/engine.js'></script>
        <script type='text/javascript' src='<%= request.getContextPath() %>/dwr/util.js'></script>
        <script type='text/javascript' src='<%= request.getContextPath() %>/dwr/interface/alunoDAO.js'></script>
        
        <script type='text/javascript'>
            function pesquisar() {
                alunoDAO.find();
            }
        </script>
    </head>
    <body>
        <h3>teste DWR</h3>
        <input type="button" value="Pesquisar" onclick="pesquisar();" />
    </body>
</html>
