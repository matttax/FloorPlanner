package service.db;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DbInit {
    private final JdbcTemplate source;

    private String getSQL(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(name),
                StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public void create() throws SQLException, IOException {
        String currentFolder = Paths.get("").toAbsolutePath().toString();
        String[] sql = getSQL(currentFolder + "/src/main/SQLScripts/create_db.sql").split(";");
        for (String str : sql) {
            source.statement(stmt -> {
                stmt.execute(str);
            });
        }
    }
}
