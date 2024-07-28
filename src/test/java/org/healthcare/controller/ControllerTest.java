package org.healthcare.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.healthcare.dao.DAO;
import org.healthcare.dao.JavaBeans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ControllerTest {

    @InjectMocks
    private Controller controller;

    @Mock
    private DAO dao;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private StringWriter stringWriter;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGetMain() throws Exception {
        when(request.getServletPath()).thenReturn("/main");

        controller.doGet(request, response);

        verify(request).getRequestDispatcher("index.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetAtualizarTabela() throws Exception {
        when(request.getServletPath()).thenReturn("/atualizarTabela");
        when(request.getSession()).thenReturn(session);

        List<String[]> lstResultados = new ArrayList<>();
        lstResultados.add(new String[]{"1", "30", "M", "Autorizado"});
        when(session.getAttribute("lstResultados")).thenReturn(lstResultados);

        controller.doGet(request, response);

        verify(response).setContentType("text/html");
        verify(response).setCharacterEncoding("UTF-8");

        String output = stringWriter.toString();
        assert output.contains("<table id='table-container' class='table'>");
        assert output.contains("<thead>");
        assert output.contains("<tr>");
        assert output.contains("<th>Procedimento</th>");
        assert output.contains("<td>1</td>");
        assert output.contains("<td>30</td>");
        assert output.contains("<td>M</td>");
        assert output.contains("<td>Autorizado</td>");
        assert output.contains("</tr>");
        assert output.contains("</table>");
    }

    @Test
    void testAutorizar() throws Exception {
        when(request.getParameter("cdProcedimento")).thenReturn("1");
        when(request.getParameter("nrIdade")).thenReturn("30");
        when(request.getParameter("sexo")).thenReturn("M");
        when(request.getSession()).thenReturn(session);

        when(dao.verificarProcedimentoExistente(1)).thenReturn(true);
        when(dao.autorizarProcedimento(any(JavaBeans.class))).thenReturn("Autorizado");

        controller.autorizar(request, response);

        verify(session).setAttribute(eq("lstResultados"), anyList());
        verify(response).setContentType("text/html");
        verify(response).getWriter();
    }
}
