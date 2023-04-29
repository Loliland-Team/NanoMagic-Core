package lolimods.adds.lolicore.util.render.shader;

import lolimods.adds.lolicore.util.render.shader.ShaderType;

public interface IShader {
	ShaderType getType();

	String getSource();
}
