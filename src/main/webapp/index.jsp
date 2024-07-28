<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %> <!-- Adicione esta linha -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Autorizador</title>
<link rel="stylesheet" href="css/styles.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="js/confirmador.js"></script>
</head>
<body>
    <h1>Autorizador de procedimentos</h1>
    <form name="frmAutorizador" id="frmAutorizador" action="autorizar" method="post">
        <table>
            <tr>
                <td><input type="number" id="cdProcedimento" name="cdProcedimento" placeholder="Procedimento" class="input-field" required></td>
            </tr>
            <tr>
                <td><input type="number" id="idade" name="nrIdade" placeholder="Idade" class="input-field" min="0" max="200" required></td>
            </tr>
            <tr>
                <td>
                    <select name="sexo" id="sexo" class="input-field" required>
                        <option value="">Selecione</option>
                        <option value="M">Masculino</option>
                        <option value="F">Feminino</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="Auditar" class="btn-primary">
    </form>

    <!-- Exibe a lista de resultados -->
    <h2>Histórico de solicitações</h2>
    <div class="table-container" id="table-container">
        <table class="table">
            <thead>
                <tr>
                    <th>Procedimento</th>
                    <th>Idade</th>
                    <th>Sexo</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Recupera a lista de resultados da sessão
                    List<String[]> lstResultados = (List<String[]>) session.getAttribute("lstResultados");
                    if (lstResultados != null && !lstResultados.isEmpty()) {
                        for (String[] resultado : lstResultados) {
                %>
                <tr>
                    <td><%= resultado[0] %></td>
                    <td><%= resultado[1] %></td>
                    <td><%= resultado[2] %></td>
                    <td><%= resultado[3] %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="4">Não existem registros a serem exibidos.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
