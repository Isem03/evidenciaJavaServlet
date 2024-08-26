import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        String descrip = request.getParameter("descrip");
        String cliente = request.getParameter("cliente");
        String cantidad = request.getParameter("cantidad");
        String material = request.getParameter("material");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Conectar a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordenprodu", "root", "");

            // Insertar datos
            String sql = "INSERT INTO orden (numeroOrden,descripcion,cliente,cantidad,material) VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, op);
            pstmt.setString(2, descrip);
            pstmt.setString(3, cliente);
            pstmt.setString(4, cantidad);
            pstmt.setString(5, material);
            pstmt.executeUpdate();

            // Respuesta al cliente
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Registro exitoso</h2>");
            
        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
