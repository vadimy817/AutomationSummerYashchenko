import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

// Реєструю сервлет на сервері
@WebServlet(name = "StudentName", value = "/name")
public class StudentName extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Сторінка студента</title></head>");
        out.println("<body>");
        out.println("<h1>Студент: Ященко</h1>");
        out.println("<br><a href='" + request.getContextPath() + "/'>На головну</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
