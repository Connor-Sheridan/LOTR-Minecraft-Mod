package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUrukHelmet extends LOTRModelBiped
{
	private ModelRenderer crest;
	private ModelRenderer jaw;
	
	public LOTRModelUrukHelmet()
	{
		this(0F);
	}
	
	public LOTRModelUrukHelmet(float f)
	{
		super(f);
		
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, f);
		bipedHead.setRotationPoint(0F, 0F, 0F);
		
		crest = new ModelRenderer(this, 32, 0);
		crest.addBox(-7F, -16F, -1F, 14, 10, 0, 0F);
		crest.rotateAngleX = (float)Math.toRadians(-10D);
		bipedHead.addChild(crest);
		
		jaw = new ModelRenderer(this, 0, 16);
		jaw.addBox(-6F, 2F, -4F, 12, 6, 0, 0F);
		jaw.rotateAngleX = (float)Math.toRadians(-60D);
		bipedHead.addChild(jaw);

		bipedHeadwear.showModel = false;
		bipedBody.showModel = false;
		bipedRightArm.showModel = false;
		bipedLeftArm.showModel = false;
		bipedRightLeg.showModel = false;
		bipedLeftLeg.showModel = false;
	}
}
