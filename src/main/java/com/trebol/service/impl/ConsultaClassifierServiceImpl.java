package com.trebol.service.impl;

import com.trebol.service.ConsultaCategoria;
import com.trebol.service.ConsultaClassifierService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Set;

@Service
public class ConsultaClassifierServiceImpl implements ConsultaClassifierService {

    private final Set<String> plantaKeywords = new HashSet<>();
    private final Set<String> citaKeywords = new HashSet<>();
    private final Set<String> riesgoKeywords = new HashSet<>();

    public ConsultaClassifierServiceImpl() {
        List<String> plantas = Arrays.asList(
                "plantas interiores","plantas exteriores","cactus","suculentas","palmeras","bonsái","bonsais","bonsai","orquídeas","orquideas","flores",
                "fertilizantes","fertilizante","riego","regar","iluminación","iluminacion","humedad","trasplante","trasplantes","sustrato","sustratos",
                "poda","reproducción","reproduccion","enfermedad","enfermedades","plaga","plagas","hojas amarillas","hojas secas","crecimiento"
        );

        List<String> citas = Arrays.asList(
                "cotización","cotizacion","presupuesto","mantenimiento","asesoría","asesoria","visita técnica","visita tecnica","diseño de jardines","paisajismo",
                "contratación","contratacion","servicio profesional","cita","agenda","reservar","reservación","inspección","inspeccion","especialista","asesoría profesional"
        );

        // Additional phrases and synonyms that imply intention to hire/visit/request professional help
        List<String> citasIntencion = Arrays.asList(
            "cuánto cuesta","cuanto cuesta","cuánto me cuesta","cuanto me cuesta","cuánto cuesta que","cuanto cuesta que",
            "necesito ayuda","necesito ayuda profesional","necesito una visita","necesito una visita técnica","quiero que alguien",
            "quiero que alguien vea","quiero que revisen","quiero que revisen mi jardín","quiero que revisen mi jardin","tengo un problema grande","necesito una visita",
            "me gustaría una visita","me gustaria una visita","quisiera una visita","quisiera presupuesto","me pueden ayudar con el diseño","me pueden ayudar"
        );

        List<String> riesgos = Arrays.asList(
                "árbol cayéndose","árbol cayendose","árbol inclinado","arbol inclinado","árbol peligroso","arbol peligroso","ramas sobre cables","ramas sobre techo",
                "raíces dañando tuberías","raices dañando tuberias","raíces dañando construcción","raices dañando construccion","árbol grande enfermo","arbol grande enfermo",
                "mascota intoxicada","perro comió planta","perro comio planta","gato comió planta","gato comio planta","niño comió planta","nino comio planta",
                "planta venenosa","intoxicación","intoxicacion","infestación severa","infestacion severa","plaga grave","arbol cayendo","arbol peligroso"
        );

        plantaKeywords.addAll(plantas);
        citaKeywords.addAll(citas);
        citaKeywords.addAll(citasIntencion);
        riesgoKeywords.addAll(riesgos);
    }

    @Override
    public ConsultaCategoria clasificar(String pregunta, String tema) {
        String text = (pregunta == null ? "" : pregunta);
        String t = (tema == null ? "" : tema);

        String ntext = normalize(text);
        String ntema = normalize(t);

        // check high risk explicit keywords first (normalized)
        for (String k : riesgoKeywords) {
            String nk = normalize(k);
            if (ntext.contains(nk) || ntema.contains(nk)) {
                return ConsultaCategoria.RIESGO_ALTO;
            }
        }

        // Co-occurrence rules for high risk (covers phrases not in keyword list)
        // Trees falling / leaning / dangerous
        if ((ntext.contains("arbol") || ntext.contains("arboles")) && containsAny(ntext, "caer", "cayend", "inclin", "inclinado", "peligro", "peligros", "derrumb", "derrumbar", "caida", "podria caerse", "podria caerse")) {
            return ConsultaCategoria.RIESGO_ALTO;
        }

        // Branches over cables/roof
        if (ntext.contains("rama") || ntext.contains("ramas")) {
            if (containsAny(ntext, "cable", "cables", "techo", "electri", "eléctr", "electric")) {
                return ConsultaCategoria.RIESGO_ALTO;
            }
        }

        // Roots damaging pipes / constructions
        if (containsAny(ntext, "raiz", "raices", "raíces")) {
            if (containsAny(ntext, "tuber", "tuberia", "tuberías", "tuberias", "cañería", "canon", "tuber", "agua", "ciment", "cimientos", "muro", "pared", "edificio", "construcc")) {
                return ConsultaCategoria.RIESGO_ALTO;
            }
        }

        // Pets / children poisoned / ate plant
        if (containsAny(ntext, "mascota", "perro", "perros", "gato", "gatos", "niño", "nino", "hijo", "hija", "ninos", "niños")) {
            if (containsAny(ntext, "intoxic", "envenen", "vomit", "comio", "comió", "mareo", "diarrea", "dolor" , "convuls" , "problema" , "sintoma")) {
                return ConsultaCategoria.RIESGO_ALTO;
            }
        }

        // check redirect to appointment
        for (String k : citaKeywords) {
            String nk = normalize(k);
            if (ntext.contains(nk) || ntema.contains(nk)) {
                return ConsultaCategoria.REDIRECCION_CITA;
            }
        }

        // Intent-based detection: verbs of need + service words
        List<String> necesidadVerbos = Arrays.asList("necesito","quiero","quisiera","me gustaría","me gustaria","podrían","pueden","por favor");
        List<String> servicioNouns = Arrays.asList("visita","revisen","revisar","revisión","revision","presupuesto","cotización","cotizacion","precio","contratar","contratación","contratacion","servicio","asesoría","asesoria","ayuda","inspección","inspeccion","mantenimiento","diseño");

        boolean tieneVerboNecesidad = false;
        for (String v : necesidadVerbos) {
            if (text.contains(v)) {
                tieneVerboNecesidad = true;
                break;
            }
        }

        if (tieneVerboNecesidad) {
            for (String s : servicioNouns) {
                if (text.contains(s)) {
                    return ConsultaCategoria.REDIRECCION_CITA;
                }
            }
        }

        // check plant consultation
        for (String k : plantaKeywords) {
            String nk = normalize(k);
            if (ntext.contains(nk) || ntema.contains(nk)) {
                return ConsultaCategoria.CONSULTA_PLANTA;
            }
        }

        // default: if none matched, treat as plant consultation
        return ConsultaCategoria.CONSULTA_PLANTA;
    }

    private static String normalize(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return n.toLowerCase(Locale.ROOT);
    }

    private static boolean containsAny(String text, String... patterns) {
        if (text == null) return false;
        for (String p : patterns) {
            if (p == null) continue;
            if (text.contains(p)) return true;
        }
        return false;
    }
}
