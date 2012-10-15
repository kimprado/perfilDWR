<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.perfil.dwr.dao.AlunoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%!
    public static int contador = 0;
    private static final SimpleDateFormat fmDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public void exibeResultado() {
        ++contador;
        String dataHora = fmDataHora.format(new Date());
        System.out.println("contador: " + dataHora + " - " + contador);
    }
%>
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
            
            function pesquisar2() {
                contador++;
                if (contador < 50) {
                    alunoDAO.find('2', exibe);
                    document.getElementById('divIdAluno').innerHTML += '(' + contador + ') Enviado: <br/>';
                    //setTimeout('pesquisar();', 500);
                } else if (contador == 500) {
                    //alert('fim do teste com iterações: ' + contador);
                    document.getElementById('divIdAluno').innerHTML += '<br/>Fim.';
                }
            }
            
            function pesquisar() {
                //contador++;
                for (var i=0; i < 500; i++) {
                    ///alunoDAO.find('2', exibe);
                    document.getElementById('divIdAluno').innerHTML += '(' + i + ') Enviado: <br/>';
                    //setTimeout('pesquisar();', 500);
                    setTimeout('chamada();', 5);
                }
            }
            
            function chamada() {
                alunoDAO.find('2', exibe);
            }
            
            function exibe(aluno) {
                contador++;
                //pesquisar();
                document.getElementById('divIdAluno').innerHTML += '(' + contador + ') Aluno: ' + aluno.nome + ' - ' + aluno.telefone + '<br/>';
            }
            
            function limpar() {
                contador = 0;
                document.getElementById('divIdAluno').innerHTML = '';
            }
            
        </script>

    </head>

    <body onload="//pesquisar();">
        <h3>teste DWR</h3>
        <input type="button" value="Pesquisar" onclick="pesquisar();" />&nbsp;
        <input type="button" value="Limpar" onclick="limpar();" />
        <br><br>
        <div id="divIdAluno">
            
        </div>
    </body>
    <%
        //System.out.println("contador: " + (++contador));
        exibeResultado();
        AlunoDAO alunoDAO =new AlunoDAO();
        alunoDAO.find("3");
    %>
    
    
</html>