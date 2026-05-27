package com.github_proxy;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class GithubProxyApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@RegisterExtension
	static WireMockExtension wireMockServer = WireMockExtension.newInstance()
			.options(
					wireMockConfig()
							.dynamicPort()
							.usingFilesUnderClasspath("wiremock")
			)
			.build();

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("github.api.url", wireMockServer::baseUrl);
	}

	@Test
	void shouldReturnOnlyNotForkRepository() throws Exception {
		String username = "Adam";


		this.mockMvc.perform(get("/api/githubProxy/{username}", username))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].owner", is(username)))
				.andExpect(jsonPath("$[0].branches[0].name", is("main")))
				.andExpect(jsonPath("$[0].branches[0].sha", is("1234567890abcdef")))
				.andExpect(jsonPath("$.length()", is(1)));

	}

	@Test
	void shouldReturnEmptyListWhenUserHasOnlyForks() throws Exception {
		String username = "Maciej";

		this.mockMvc.perform(get("/api/githubProxy/{username}", username))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void shouldReturn404WhenGithubUserNotFound() throws Exception {
		String username = "NonExistentUser";

		this.mockMvc.perform(get("/api/githubProxy/{username}", username))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

}
