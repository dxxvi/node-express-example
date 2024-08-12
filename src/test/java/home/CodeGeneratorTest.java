package home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CodeGeneratorTest {
  private static final Logger log = LoggerFactory.getLogger(CodeGeneratorTest.class);

  @Test
  void testGenerateCode() {
    BiFunction<String, String, String> toSqlType =
        (s, t) -> s.toLowerCase().replace(" byte", "") + ("Yes".equals(t) ? "," : "");

    String csv =
        """
            API_MTDAT_APPL_ID	CHAR(36 BYTE)	No
            APPL_ID	VARCHAR2(20 BYTE)	No
            API_CLNT_ID	VARCHAR2(128 BYTE)	No
            OBJ_STAT_TXT	VARCHAR2(255 BYTE)	Yes
            OBJ_STAT_DT	DATE	Yes
            OBJ_STAT_UPDT_BY_ID	VARCHAR2(64 BYTE)	Yes
            OBJ_STAT_UPDT_BY_ID_TYPE_NM	VARCHAR2(128 BYTE)	Yes
            OBJ_STAT_UPDT_BY_NM	VARCHAR2(128 BYTE)	Yes
            OBJ_STAT_UPDT_EMAIL_ADDR_TXT	VARCHAR2(255 BYTE)	Yes
            OBJ_STAT_CMNT_TXT	VARCHAR2(1024 BYTE)	Yes
            LD_IND	CHAR(1 BYTE)	Yes
            LD_DTTM	TIMESTAMP(6)	Yes
            API_MTDAT_APPL_LFCYC_STAT_TXT	VARCHAR2(255 BYTE)	Yes
            RUN_ID	VARCHAR2(32 BYTE)	No
            CRTE_DTTM	TIMESTAMP(6)	Yes
            CRTE_BY_ID	VARCHAR2(20 BYTE)	Yes
            UPDT_DTTM	TIMESTAMP(6)	Yes
            UPDT_BY_ID	VARCHAR2(20 BYTE)	Yes
            ERR_FLG_IND	CHAR(1 BYTE)	Yes
            ERR_TXT	VARCHAR2(255 BYTE)	Yes""";
    List<String[]> stringArrays =
        csv.lines()
            .map(line -> line.split("\t"))
            .map(
                arr ->
                    new String[] {
                      arr[0],
                      toSqlType.apply(arr[1], arr[2]),
                      "No".equals(arr[2]) ? " not null," : ""
                    })
            .toList();
    int columnNameWidth = stringArrays.stream().mapToInt(arr -> arr[0].length()).max().getAsInt();
    int columnTypeWidth = stringArrays.stream().mapToInt(arr -> arr[1].length()).max().getAsInt();
    stringArrays.forEach(
        arr ->
            System.out.printf(
                "%-" + columnNameWidth + "s %-" + columnTypeWidth + "s%s\n",
                arr[0],
                arr[1],
                arr[2]));

    csv.lines()
        .map(l -> l.split("\t"))
        .map(
            arr ->
                """
            @Column(name = "%s"%s)
            private %s %s;\n"""
                    .formatted(
                        arr[0],
                        arr[2].equals("No") ? ", nullable = false" : "",
                        toJavaType(arr[1], arr[2]),
                        toVariableName(arr[0])))
        .forEach(System.out::println);
  }

  private static String toVariableName(String columnName) {
    StringBuilder sb = new StringBuilder();
    boolean prevCharIsUnderscore = false;
    for (char c : columnName.toCharArray()) {
      if (c == '_') {
        prevCharIsUnderscore = true;
        continue;
      }
      if (prevCharIsUnderscore) {
        sb.append(Character.toUpperCase(c));
        prevCharIsUnderscore = false;
      } else {
        sb.append(Character.toLowerCase(c));
      }
    }
    return sb.toString();
  }

  private String toJavaType(String s, String nullable) {
    if (s.startsWith("VARCHAR2")) return "String";
    if (s.startsWith("CHAR(1 ")) {
      if (nullable.equals("Yes")) return "Character";
      else return "char";
    }
    if (s.startsWith("CHAR(")) return "String";
    if (s.equals("DATE")) return "LocalDateTime";
    if (s.startsWith("TIMESTAMP(")) return "LocalDateTime";
    log.error("What's this? {}", s);
    return s;
  }

  @Test
  void deleteMe() {
    Map<String, Object> m1 = new HashMap<>();
    m1.put("m1-k1", "v1");
    m1.put("m1-k2", 2);

    Map<String, Object> m2 = new HashMap<>(m1);
    m2.put("m2-k1", 3);
    m2.put("m2-k2", true);
    System.out.println(m2);
  }
}
