package com.seunghoo.na.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seunghoo.na.dao.UserDao;
import com.seunghoo.na.domain.User;
@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserDao userDao;
	
	@Override
	public String emailcheck(HttpServletRequest request) {

		String email = request.getParameter("email");
		return userDao.emailcheck(email);
	}

	@Override
	public void register(MultipartHttpServletRequest request) {
		//파라미터 읽기
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String pw = request.getParameter("pw");
		//파일은 읽는 방법이 다름
		MultipartFile image = request.getFile("image");
		//파일을 저장할 경로 만들기
		//파일은 절대경로로만 저장이 가능
		//프로젝트 내의 userimage 디렉토리의 절대 경로를 만들기
		String uploadPath = request.getRealPath("/userimage");
		//랜덤한 64자리의 문자열 만들기
		UUID uid = UUID.randomUUID();
		//원본 파일이름 가져오기
		String filename = image.getOriginalFilename();
		filename = uid + "_" + filename;
		//업로드할 파일의 실제 경로 만들기
		String filepath = uploadPath + "\\" + filename;
		
		//Dao의 파라미터로 사용할 객체
		User user = new User();
		//업로드할 파일 객체 만들기
		File f = new File(filepath);
		try{
			//파일 전송 - 파일 업로드
			image.transferTo(f);
			//Dao의 파라미터 만들기
			user.setEmail(email);
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);
			//데이터베이스에는 파일 이름만 저장
			user.setImage(filename);
			userDao.register(user);
		}catch(Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}
		
	}

	
	@Override
	public User login(HttpServletRequest request) {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		//Dao 메소드 호출 
		User user = userDao.login(email);
		//비밀번호가 일치하면 
		
		//클라이언트가 요청 pw를 가져오고 DAO 메소드 호출을 통해 
		// 두개의 비밀번호가 같은지 확인합니다.
		//만약 두개의 비밀번호가 일치하면 데이터를 클라이언트에게 보내기전 
		//비밀번호만 리셋을 하고 
		//두개의 비밀번호가 일치하지 않을경우 user의 모든 내용을 null로 리턴합니다.
		if(user!=null) {
			if(BCrypt.checkpw(pw, user.getPw())==true);
			//비밀번호 초기화
			user.setPw("");
			//비밀번호가 일치하지 않으면 전부 초기화
		}else {
				user =null;
		}
		return user;
	}

	@Override
	public String address(String loc) {
		String [] ar  = loc.split("-");
		
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
		addr= addr+"x=" +ar[1] +"&y="+ar[0];
		//위의 주소를 가지고 URL 객체를 생성 
		try {
			//위의 주소를 가지고 URL 객체를 생성 
			URL url = new URL(addr);
			//URL 객체를 가지고 HttpURLConnection 객체 만들기
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			//인증부분은 받아야 하면 api에 작성되어있습니다.
			//인증받기 
			con.setRequestProperty("Authorization", "KakaoAK f767f29740a04c38b732dabe1839d6f4");
			//옵션 설정 
			con.setConnectTimeout(20000);
			con.setUseCaches(false);
			//줄단위 데이터 읽기 
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			while(true) {
				String line =br.readLine();
				if(line ==null) {
					break;
							}
				//읽은 데이터가 있으면 sb에추가 
				sb.append(line);
			}
			br.close();
			con.disconnect();

			System.out.println(sb);
			System.out.println("----------------");
			JSONObject obj = new JSONObject(sb.toString());
			System.out.println(obj);
			System.out.println("----------------");
			JSONArray imsi = obj.getJSONArray("documents");
			System.out.println(imsi);
			System.out.println("----------------");
			JSONObject o = imsi.getJSONObject(0);
			System.out.println(o);
			System.out.println("----------------");
			JSONObject c = o.getJSONObject("address");
			String address= c.getString("address_name");
			System.out.println(address);
			System.out.println("----------------");
			return address;
		}catch (Exception e) {
			System.out.println("지도보이기예외" + e.getMessage());
			e.printStackTrace();
		}
		
		
		return null;
	}

	//메일을 보내주는 객체를 주입
	@Autowired
	private MailSender mailSender;
	
	
	@Override
	public void sendEmail(HttpServletRequest request) {
		
		String receiver =request.getParameter("receiver");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("ggangpae3@naver.com");
			//받는 사람 설정 
			message.setTo(receiver);
			//메이제목설정 
			message.setSubject(title);
			//메일 내용설정 
			message.setText(content);
			//메일 보내기 
			mailSender.send(message);
		}catch(Exception e) {
			System.out.println("email 예외 "+e.getMessage());
			e.printStackTrace();
		}
		
	}


	
	
	
	
	

}
