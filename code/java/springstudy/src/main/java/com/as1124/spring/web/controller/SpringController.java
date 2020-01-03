package com.as1124.spring.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.as1124.spring.web.controller.model.Spittle;

/**
 * {@link GetMapping} 等价于 {@link RequestMapping} 的GET调用。<br/>
 * <p>
 * 需要注意一下几点：
 * <ul>
 * <li>请求路径匹配 {@link RequestMapping}
 * <li>如何获取并处理请求参数：{@link RequestParam}，{@link PathVariable}，
 * <li>如何返回数据：{@link Model}, 或者直接返回
 * </ul>
 * </p>
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping(path = { "/" })
public class SpringController {

	/**
	 *  处理路径 <code>/</code> 的GET请求，返回视图名为 <code>home</code>
	 *  
	 * @param data2Return 和前端进行数据交互时的数据通道
	 * @return 请求路径对应的视图{@link View}名称
	 */
	@RequestMapping(path = "/home", method = { RequestMethod.GET })
	public String homePage(Model data2Return) {
		// 处理需要返回到前端界面的数据
		List<String> array = new ArrayList<>();
		array.add("Hello ");
		array.add("Spring World!!!");
		data2Return.addAttribute("names", array);

		// 返回视图名称，翻译后  =>  /views/home/home.jsp
		return "/home/home.jsp";
	}

	@GetMapping("/user")
	public List<String> queryParams(@RequestParam(value = "ageFrom", defaultValue = "0") int ageFrom) {
		// 查询参数注解
		List<String> names = new ArrayList<>();
		names.add("Bob");
		names.add("Jack");
		return names;
	}

	@GetMapping("/user/{userid}")
	public String pathParams(@PathVariable(value = "userid") String userid) {
		// 路径参数注解
		return "中国上海_zh_CN";
	}

	@PostMapping("/user/register")
	public String formParams(Spittle spittle) {
		// 表单参数转对象
		return "success";
	}

	/**
	 * 表单校验：前提是校验对象的待校验属性、字段有相应的校验注解；<code>javax.validation.constraints</code>
	 * <br/>参考 {@link Spittle}
	 * @param spittle
	 * @param errors 校验后的结果
	 * @return
	 */
	@PostMapping(value = "/user/regist_new", consumes = { "application/x-www-form-urlencoded;charset=UTF-8",
			"application/json;charset=UTF-8" })
	public String formValidation(@Valid Spittle spittle, Errors errors) {
		//ATTENTION 为什么一直校验都是没错呢
		// 表单校验
		if (errors.hasErrors()) {
			return "fillForm";
		} else {
			return "redirect:/user/" + spittle.getId();
		}
	}

	@GetMapping(path = "/*")
	public String defaultPage() {
		return "newIndex.html";
	}
}
