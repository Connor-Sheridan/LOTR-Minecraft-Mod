package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHalfTroll extends LOTRModelBiped
{
	public LOTRModelHalfTroll()
	{
		this(0F);
	}
	
	public LOTRModelHalfTroll(float f)
	{
		super(f);
		
		textureWidth = 64;
		textureHeight = 64;

		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0F, -8F, 0F);
		bipedHead.addBox(-5F, -10F, -5F, 10, 10, 10, f);
		bipedHead.setTextureOffset(40, 5).addBox(-4F, -3F, -7F, 8, 3, 2, f);
		bipedHead.setTextureOffset(40, 10).addBox(-1F, -12.5F, -1.5F, 2, 10, 8, f);
		
		ModelRenderer nose = new ModelRenderer(this, 30, 0);
		nose.addBox(-1F, -4.5F, -8F, 2, 3, 3, f);
		nose.rotateAngleX = (float)Math.toRadians(-20D);
		bipedHead.addChild(nose);
		
		ModelRenderer teeth = new ModelRenderer(this, 50, 13);
		teeth.addBox(-3F, -6.5F, -4.5F, 1, 2, 1, f);
		teeth.mirror = true;
		teeth.addBox(2F, -6.5F, -4.5F, 1, 2, 1, f);
		teeth.rotateAngleX = (float)Math.toRadians(30D);
		bipedHead.addChild(teeth);
		
		ModelRenderer earRight = new ModelRenderer(this, 0, 0);
		earRight.addBox(-5F, -6F, -2F, 1, 3, 3, f);
		earRight.rotateAngleY = (float)Math.toRadians(-35D);
		bipedHead.addChild(earRight);
		
		ModelRenderer earLeft = new ModelRenderer(this, 0, 0);
		earLeft.mirror = true;
		earLeft.addBox(4F, -6F, -2F, 1, 3, 3, f);
		earLeft.rotateAngleY = (float)Math.toRadians(35D);
		bipedHead.addChild(earLeft);
		
		ModelRenderer hornRight1 = new ModelRenderer(this, 40, 0);
		hornRight1.addBox(-10F, -8F, 1F, 3, 2, 2, f);
		hornRight1.rotateAngleZ = (float)Math.toRadians(20D);
		bipedHead.addChild(hornRight1);
		
		ModelRenderer hornRight2 = new ModelRenderer(this, 50, 0);
		hornRight2.addBox(-14.5F, -4F, 1.5F, 3, 1, 1, f);
		hornRight2.rotateAngleZ = (float)Math.toRadians(40D);
		bipedHead.addChild(hornRight2);
		
		ModelRenderer hornLeft1 = new ModelRenderer(this, 40, 0);
		hornLeft1.mirror = true;
		hornLeft1.addBox(7F, -8F, 1F, 3, 2, 2, f);
		hornLeft1.rotateAngleZ = (float)Math.toRadians(-20D);
		bipedHead.addChild(hornLeft1);
		
		ModelRenderer hornLeft2 = new ModelRenderer(this, 50, 0);
		hornLeft2.mirror = true;
		hornLeft2.addBox(11.5F, -4F, 1.5F, 3, 1, 1, f);
		hornLeft2.rotateAngleZ = (float)Math.toRadians(-40D);
		bipedHead.addChild(hornLeft2);
		
		bipedBody = new ModelRenderer(this, 0, 20);
		bipedBody.setRotationPoint(0F, -8F, 0F);
		bipedBody.addBox(-6F, 0F, -4F, 12, 16, 8, f);
		
		bipedRightArm = new ModelRenderer(this, 20, 50);
		bipedRightArm.setRotationPoint(-8.5F, -6F, 0F);
		bipedRightArm.addBox(-3.5F, -2F, -3F, 6, 8, 6, f);
		bipedRightArm.setTextureOffset(0, 49).addBox(-3F, 6F, -2.5F, 5, 10, 5, f);
		
		bipedLeftArm = new ModelRenderer(this, 20, 50);
		bipedLeftArm.setRotationPoint(8.5F, -6F, 0F);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-2.5F, -2F, -3F, 6, 8, 6, f);
		bipedLeftArm.setTextureOffset(0, 49).addBox(-2F, 6F, -2.5F, 5, 10, 5, f);
		
		bipedRightLeg = new ModelRenderer(this, 40, 28);
		bipedRightLeg.setRotationPoint(-3F, 8F, 0F);
		bipedRightLeg.addBox(-3F, 0F, -3F, 6, 16, 6, f);
		
		bipedLeftLeg = new ModelRenderer(this, 40, 28);
		bipedLeftLeg.setRotationPoint(3F, 8F, 0F);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-3F, 0F, -3F, 6, 16, 6, f);
		
		bipedHeadwear.isHidden = true;
	}
	
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
