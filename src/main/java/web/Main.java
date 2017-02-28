package web;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.ui.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
class Main {
	// String server = "jdbc:mysql://128.199.119.79/market";
	String server = "jdbc:mysql://104.154.199.75/market";
	String user   = "market";
	String password = "m@rket";
	
	Main() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) { }
	}
	
	@RequestMapping("/")
	String showIndex(Model model) {
		LinkedList list = new LinkedList();
		try {
			Connection c = DriverManager.getConnection(
				server, user, password);
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(
				"select * from post where status='active'");
			while (r.next()) {
				String topic = r.getString("topic");
				list.add(topic);
			}
			r.close(); s.close(); c.close();
		} catch (Exception e) { }
		model.addAttribute("post", list);
		return "index";
	}
	
	@RequestMapping("/view/{topic}")
	String showDetailByTopic(@PathVariable String topic, Model model) {
		Post post = new Post();
		try {
			Connection c = DriverManager.getConnection(
							server, user, password);
			PreparedStatement p = c.prepareStatement(
				"select * from post where topic = ?");
			p.setString(1, topic);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				post.code = r.getLong("code");
				post.topic = r.getString("topic");
				post.detail = r.getString("detail");
				post.member = r.getLong("member");
				post.status = r.getString("status");
				post.updated = r.getString("updated");
			}
			r.close(); p.close(); c.close();
		} catch (Exception e) { }
		model.addAttribute("post", post);
		return "view";
	}
	
	@RequestMapping("/login")
	String showLoginPage() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	String checkLogin(String email, String password, HttpSession session) {
		boolean passed = false;
		String sql = "select * from member where email=? and password=sha2(?, 512)";
		try {
			Connection c = DriverManager.getConnection(server, user, this.password);
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, email);
			p.setString(2, password);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				passed = true;
				session.setAttribute("user", r.getString("name"));
			}
			r.close(); p.close(); c.close();
		} catch (Exception e) { }
		if (passed) {
			return "redirect:/home";
		} else {
			return "redirect:/login?error=Invalid Email or Password";
		}
	}
	
	@RequestMapping("/home")
	String showHomePage(HttpSession session) {
		String name = (String)session.getAttribute("user");
		if (name == null) {
			return "redirect:/login";
		} else {
			return "home";
		}
	}
	
	@RequestMapping("/logout")
	String showLogoutPage(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
}
