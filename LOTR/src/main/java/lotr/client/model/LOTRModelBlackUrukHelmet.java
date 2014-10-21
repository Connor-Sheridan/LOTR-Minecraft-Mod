package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackUrukHelmet extends LOTRModelBiped
{
	private ModelRenderer crest;
	
	public LOTRModelBlackUrukHelmet()
	{
		this(0F);
	}
	
	public LOTRModelBlackUrukHelmet(float f)
	{
		super(f);
		
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, f);
		bipedHead.setRotationPoint(0F, 0F, 0F);
		
		crest = new ModelRenderer(this, 32, 0);
		crest.addBox(-8F, -16F, -3F, 16, 10, 0, 0F);
		crest.rotateAngleX = (float)Math.toRadians(-20D);
		bipedHead.addChild(crest);

		bipedHeadwear.showModel = false;
		bipedBody.showModel = false;
		bipedRightArm.showModel = false;
		bipedLeftArm.showModel = false;
		bipedRightLeg.showModel = false;
		bipedLeftLeg.showModel = false;
	}
}
