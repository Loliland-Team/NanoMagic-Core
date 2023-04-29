package lolimods.adds.lolicore.client.model;

import com.google.gson.JsonObject;
import lolimods.adds.lolicore.client.model.ParameterizedItemModelLoader;
import lolimods.adds.lolicore.client.model.SumItemModel;
import lolimods.adds.lolicore.util.helper.ResourceUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class LCModels {
	public static void registerModels() {
		ModelLoaderRegistry.registerLoader(new ParameterizedItemModelLoader());
		ModelLoaderRegistry.registerLoader(new SumItemModel.Loader());
	}

	public static boolean isOfType(ResourceLocation resource, String type) {
		try {
			JsonObject model = ResourceUtils.getAsJson(resource).getAsJsonObject();
			return model.has("lc") && model.get("lc").getAsString().equals(type);
		} catch (Exception e) {
			return false;
		}
	}

	public static ResourceLocation getRealModelLocation(ResourceLocation resource) {
		return new ResourceLocation(resource.getNamespace(), (resource.getPath().startsWith("models/") ? resource.getPath() : ("models/" + resource.getPath())) + ".json");
	}
}
