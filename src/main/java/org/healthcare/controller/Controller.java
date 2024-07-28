package org.healthcare.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.healthcare.dao.DAO;
import org.healthcare.dao.JavaBeans;
import org.healthcare.utils.Sexo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/Controller", "/main", "/autorizar", "/atualizarTabela"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DAO dao = new DAO();
    private final JavaBeans autorizador = new JavaBeans();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.equals("/main")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (action.equals("/atualizarTabela")) {
            atualizarTabela(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.equals("/autorizar")) {
            autorizar(request, response);
        }
    }

    protected void autorizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        autorizador.setCdProcedimento(Integer.parseInt(request.getParameter("cdProcedimento")));
        autorizador.setNrIdade(Integer.parseInt(request.getParameter("nrIdade")));
        autorizador.setSexo(Sexo.fromValue(request.getParameter("sexo")));

        // Recupera ou inicializa a lista de lstResultados da sessão
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<String[]> lstResultados = (List<String[]>) session.getAttribute("lstResultados");
        if (lstResultados == null) {
            lstResultados = new ArrayList<>();
        }

        boolean isProcedimentoExiste = dao.verificarProcedimentoExistente(autorizador.getCdProcedimento());
        String resultado;
        if (!isProcedimentoExiste) {
            resultado = "Procedimento não encontrado";
        } else {
            resultado = dao.autorizarProcedimento(autorizador);
        }

        lstResultados.add(0, new String[]{
                String.valueOf(autorizador.getCdProcedimento()),
                String.valueOf(autorizador.getNrIdade()),
                autorizador.getSexo().getValue(),
                resultado
        });

        // Atualizar a lista de lstResultados na sessão
        session.setAttribute("lstResultados", lstResultados);

        // Gerar o HTML da tabela
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<table id='table-container' class='table'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Procedimento</th>");
        out.println("<th>Idade</th>");
        out.println("<th>Sexo</th>");
        out.println("<th>Status</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (String[] retorno : lstResultados) {
            out.println("<tr>");
            for (String valor : retorno) {
                out.println("<td>" + valor + "</td>");
            }
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");

        out.close();
    }

    // Gerar o HTML da tabela com os dados da sessão
    private void atualizarTabela(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<String[]> lstResultados = (List<String[]>) session.getAttribute("lstResultados");

        out.println("<table id='table-container' class='table'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Procedimento</th>");
        out.println("<th>Idade</th>");
        out.println("<th>Sexo</th>");
        out.println("<th>Status</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        if (lstResultados != null && !lstResultados.isEmpty()) {
            for (String[] resultado : lstResultados) {
                out.println("<tr>");
                for (String valor : resultado) {
                    out.println("<td>" + valor + "</td>");
                }
                out.println("</tr>");
            }
        } else {
            out.println("<tr><td colspan='4'>Não existem registros a serem exibidos.</td></tr>");
        }

        out.println("</tbody>");
        out.println("</table>");

        out.close();
    }
}
