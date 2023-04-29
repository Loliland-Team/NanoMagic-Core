package lolimods.adds.lolicore.util.helper;

import com.google.gson.JsonElement;
import lolimods.adds.lolicore.util.helper.JsonUtils9;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {
	public static IResource getResource(ResourceLocation resource) throws IOException {
		return Minecraft.getMinecraft().getResourceManager().getResource(resource);
	}

	public static byte[] getAsBytes(ResourceLocation resource) throws IOException {
		try (BufferedInputStream in = new BufferedInputStream(getResource(resource).getInputStream())) {
			return IOUtils.toByteArray(in);
		}
	}

	public static String getAsString(ResourceLocation resource) throws IOException {
		try (BufferedInputStream in = new BufferedInputStream(getResource(resource).getInputStream())) {
			return IOUtils.toString(in, StandardCharsets.UTF_8);
		}
	}

	public static JsonElement getAsJson(ResourceLocation resource) throws IOException {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(getResource(resource).getInputStream()))) {
			return JsonUtils9.PARSER.parse(in);
		}
	}
}
