package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelNearHaradWarlordHelmet extends LOTRModelBiped
{
	private ModelRenderer stickRight;
	private ModelRenderer stickCentre;
	private ModelRenderer stickLeft;
	
	public LOTRModelNearHaradWarlordHelmet()
	{
		this(0F);
	}
	
	public LOTRModelNearHaradWarlordHelmet(float f)
	{
		super(f);
		
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, f);
		bipedHead.setRotationPoint(0F, 0F, 0F);
		
		bipedHead.setTextureOffset(6, 24).addBox(-2.5F, -3F, 4.1F, 5, 3, 2, 0F);
		bipedHead.setTextureOffset(0, 16).addBox(-9F, -16F, 5.5F, 18, 8, 0, 0F);
		
		stickRight = new ModelRenderer(this, 36, 0);
		stickRight.addBox(-0.5F, -19F, 5F, 1, 18, 1, 0F);
		stickRight.setTextureOffset(0, 24).addBox(-1.5F, -24F, 5.5F, 3, 5, 0, 0F);
		stickRight.rotateAngleZ = (float)Math.toRadians(-28D);
		bipedHead.addChild(stickRight);
		
		stickCentre = new ModelRenderer(this, 36, 0);
		stickCentre.addBox(-0.5F, -19F, 5F, 1, 18, 1, 0F);
		stickCentre.setTextureOffset(0, 24).addBox(-1.5F, -24F, 5.5F, 3, 5, 0, 0F);
		stickCentre.rotateAngleZ = (float)Math.toRadians(0D);
		bipedHead.addChild(stickCentre);
		
		stickLeft = new ModelRenderer(this, 36, 0);
		stickLeft.addBox(-0.5F, -19F, 5F, 1, 18, 1, 0F);
		stickLeft.setTextureOffset(0, 24).addBox(-1.5F, -24F, 5.5F, 3, 5, 0, 0F);
		stickLeft.rotateAngleZ = (float)Math.toRadians(28D);
		bipedHead.addChild(stickLeft);

		bipedHeadwear.showModel = false;
		bipedBody.showModel = false;
		bipedRightArm.showModel = false;
		bipedLeftArm.showModel = false;
		bipedRightLeg.showModel = false;
		bipedLeftLeg.showModel = false;
	}
}
