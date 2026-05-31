package com.trebol.service.impl;

import com.trebol.service.ConsultaCategoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConsultaClassifierServiceImplTest {

    static class Case {
        final String pregunta;
        final String tema;
        final ConsultaCategoria expected;

        Case(String pregunta, String tema, ConsultaCategoria expected) {
            this.pregunta = pregunta;
            this.tema = tema;
            this.expected = expected;
        }
    }

    @Test
    public void testClassifierCoverage() throws IOException {
        ConsultaClassifierServiceImpl svc = new ConsultaClassifierServiceImpl();

        List<Case> cases = new ArrayList<>();

        // CONSULTA_PLANTA (covering required items)
        cases.add(new Case("¿Qué plantas son buenas para interiores?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Qué plantas puedo poner en el exterior de mi patio?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("Mi cactus tiene manchas, ¿qué le pasa?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cómo cuido las suculentas en maceta?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cómo cuidar palmeras en clima templado?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cómo podar un bonsái correctamente?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Qué necesita una orquídea para florecer?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cada cuánto fertilizo mis plantas?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Con qué frecuencia debo regar estas plantas?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cómo y cuándo realizar una poda de primavera?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cuándo es el mejor momento para trasplantar una planta?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("Mi planta tiene hojas amarillas, ¿qué hago?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("Hojas secas en hoja superior, ¿es falta de riego?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Por qué mi planta no crece?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Qué sustrato recomiendan para plantas de interior?", "", ConsultaCategoria.CONSULTA_PLANTA));

        // REDIRECCION_CITA (keywords and intent)
        cases.add(new Case("Necesito una cotización para el mantenimiento del jardín", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("¿Pueden darme un presupuesto para diseño de jardines?", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Busco asesoría profesional para mi jardín", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Quiero contratar servicio de paisajismo", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Necesito una visita técnica para evaluar el terreno", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("¿Cuánto cuesta que revisen mi jardín?", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Necesito ayuda profesional.", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Quiero que alguien vea mis plantas.", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Tengo un problema grande en mi jardín.", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Necesito una visita.", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("Me gustaría una visita para presupuesto", "", ConsultaCategoria.REDIRECCION_CITA));

        // RIESGO_ALTO (severe cases)
        cases.add(new Case("Un árbol está cayéndose hacia la calle", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("El árbol está inclinado y peligroso", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Ramas grandes sobre cables eléctricos", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Raíces están dañando la tubería de agua", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Raíces están dañando la construcción de mi casa", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Mi mascota está intoxicada por una planta", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Mi perro comió una planta y vomitó", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Mi hijo comió una planta y parece intoxicado", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Plaga grave en todo el huerto, infestación severa", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Infestación severa de insectos que arrasa las plantas", "", ConsultaCategoria.RIESGO_ALTO));

        // Priority tests: phrases that could match multiple categories -> expect RIESGO_ALTO
        cases.add(new Case("Un árbol grande está enfermo y podría caerse, necesito presupuesto", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("Ramas sobre cables y además quisiera contratar servicio", "", ConsultaCategoria.RIESGO_ALTO));

        // Edge cases and more plant coverage to reach 40+
        cases.add(new Case("¿Cómo cuidar orquídeas en interiores con poca luz?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Qué abono usar para palmeras?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("¿Cómo reproducir suculentas por esquejes?", "", ConsultaCategoria.CONSULTA_PLANTA));
        cases.add(new Case("Solicito inspección para verificar raíces que dañan acera", "", ConsultaCategoria.REDIRECCION_CITA));
        cases.add(new Case("¿Me pueden ayudar con el diseño del paisaje de mi casa?", "", ConsultaCategoria.REDIRECCION_CITA));

        // Ensure coverage of terms in Spanish variants
        cases.add(new Case("Tengo plaga grave en las palmeras, se está extendiendo", "", ConsultaCategoria.RIESGO_ALTO));
        cases.add(new Case("¿Cada cuánto trasplantar un árbol joven?", "", ConsultaCategoria.CONSULTA_PLANTA));

        // total count
        Assertions.assertTrue(cases.size() >= 40, "Se deben incluir al menos 40 casos de prueba, actualmente: " + cases.size());

        List<String> lines = new ArrayList<>();
        lines.add("Pregunta,Esperada,Obtenida,Resultado");

        boolean allMatched = true;
        for (Case c : cases) {
            ConsultaCategoria obtained = svc.clasificar(c.pregunta, c.tema);
            boolean matched = obtained == c.expected;
            if (!matched) allMatched = false;
            lines.add(escape(c.pregunta) + "," + c.expected.name() + "," + obtained.name() + "," + (matched ? "OK" : "MISMATCH"));
        }

        // write report to target
        Path out = Paths.get("target", "classifier-report.csv");
        Files.createDirectories(out.getParent());
        Files.write(out, lines, StandardCharsets.UTF_8);

        // Assert all matched to surface failures — report available for analysis
        Assertions.assertTrue(allMatched, "Algunas clasificaciones no coinciden. Revisa target/classifier-report.csv para detalles.");
    }

    private String escape(String s) {
        if (s == null) return "";
        String q = s.replace("\"", "\"\"");
        return "\"" + q + "\"";
    }
}
