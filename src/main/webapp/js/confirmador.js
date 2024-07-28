/**
 * Validação de formulário
 * @author Felipe Feyh
 */

$(document).ready(function() {
    $("form[name='frmAutorizador']").on("submit", function(event) {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        var procedimento = $("#cdProcedimento").val();
        var idade = $("#idade").val();
        var sexo = $("#sexo").val();

        // Validação dos campos
        if (procedimento === "" || idade === "" || sexo === "") {
            alert("Todos os campos são obrigatórios.");
            return false;
        }

        if (isNaN(procedimento) || isNaN(idade)) {
            alert("Procedimento e Idade devem ser números.");
            return false;
        }

        if (idade < 0 || idade > 200) {
            alert("A idade deve estar entre 0 e 200 anos.");
            return false;
        }

        // Envio da requisição AJAX
        $.ajax({
            url: "autorizar",
            type: "POST",
            data: $(this).serialize(),
            success: function() {
                // Atualiza a tabela com os novos dados
                $.ajax({
                    url: "atualizarTabela",
                    type: "GET",
                    success: function(response) {
                        $("#table-container").html(response);
                    },
                    error: function(xhr, status, error) {
                        console.error("Erro ao carregar a tabela:", status, error);
                    }
                });
                // Limpa os campos do formulário
                $("form[name='frmAutorizador']")[0].reset();

                $("#cdProcedimento").focus();
            },
            error: function(xhr, status, error) {
                console.error("Erro ao enviar a requisição:", status, error);
            }
        });

        return false;
    });

    // Atualiza a tabela ao carregar a página
    $.ajax({
        url: "atualizarTabela",
        type: "GET",
        success: function(response) {
            $("#table-container").html(response);
        },
        error: function(xhr, status, error) {
            console.error("Erro ao carregar a tabela:", status, error);
        }
    });
});
