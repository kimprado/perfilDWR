<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv=pragma content=no-cache>
        <title>Perfil DWR</title>
        
        <script type='text/javascript' src='/perfilDWR/dwr/engine.js'></script>
        <script type='text/javascript' src='/perfilDWR/dwr/util.js'></script>
        <script type='text/javascript' src='/perfilDWR/dwr/interface/alunoDAO.js'></script>
        
        <script>
            dwr.util.setEscapeHtml(false);
            
            function pesquisar() {
                alunoDAO.find('2');
            }

        </script>

    </head>

    <body >
        <h3>teste DWR</h3>
        <input type="button" value="Pesquisar" onclick="pesquisar();" />
    </body>
</html>