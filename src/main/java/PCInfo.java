import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean; // Дозволяє отримати фізичну RAM
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "PCInfo", value = "/system-info")
public class PCInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long totalRamBytes = osBean.getTotalMemorySize();
        long freeRamBytes = osBean.getFreeMemorySize();

        double totalRamGb = totalRamBytes / (1024.0 * 1024.0 * 1024.0);
        double freeRamGb = freeRamBytes / (1024.0 * 1024.0 * 1024.0);
        double usedRamGb = totalRamGb - freeRamGb;

        // Дані про CPU та ОС
        int cpuCores = runtime.availableProcessors();
        String osName = osBean.getName();
        String osArch = osBean.getArch();
        String osVersion = osBean.getVersion();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Деталі сервера</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f6f9; }");
        out.println("table { border-collapse: collapse; width: 60%; background: #fff; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        out.println("th { background-color: #007bff; color: white; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Інформація про комп'ютер, де запущено сервер:</h2>");
        out.println("<table>");
        out.println("<tr><th>Характеристика</th><th>Значення</th></tr>");
        out.println("<tr><td>Операційна система</td><td>" + osName + " (" + osVersion + ")</td></tr>");
        out.println("<tr><td>Архітектура ОС</td><td>" + osArch + "</td></tr>");
        out.println("<tr><td>Кількість логічних ядер CPU</td><td>" + cpuCores + "</td></tr>");
        out.println("<tr><td>Загальний обсяг RAM</td><td>" + String.format("%.2f", totalRamGb) + " GB</td></tr>");
        out.println("<tr><td>Використано RAM</td><td>" + String.format("%.2f", usedRamGb) + " GB</td></tr>");
        out.println("<tr><td>Вільна RAM</td><td>" + String.format("%.2f", freeRamGb) + " GB</td></tr>");
        out.println("</table>");

        out.println("<br><br><a href='" + request.getContextPath() + "/'>На головну сторінку</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
