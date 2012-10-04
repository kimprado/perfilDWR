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
            
            var contador = 0;
            
            function pesquisar() {
                contador++;
                if (contador < 500) {
                    alunoDAO.find('2', exibe);
                    setTimeout('pesquisar();', 1000);
                } else if (contador == 500) {
                    //alert('fim do teste com iterações: ' + contador);
                    document.getElementById('divIdAluno').innerHTML += '<br/>Fim.';
                }
            }
            
            function exibe(aluno) {
                pesquisar();
                document.getElementById('divIdAluno').innerHTML += '(' + contador + ') Aluno: ' + aluno.nome + ' - ' + aluno.telefone + '<br/>';
            }
            
            function limpar() {
                contador = 0;
                document.getElementById('divIdAluno').innerHTML = '';
            }
            
        </script>

    </head>

    <body >
        <h3>teste DWR</h3>
        <input type="button" value="Pesquisar" onclick="pesquisar();" />&nbsp;
        <input type="button" value="Pesquisar" onclick="limpar();" />
        
        <br><br>
        <div id="divIdAluno">
            
        </div>
    </body>
</html>