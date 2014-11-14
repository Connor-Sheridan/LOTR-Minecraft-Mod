package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBiped extends ModelBiped
{
	private boolean setup = false;
	
	private float base_bodyRotateX;
	private float base_armX;
	private float base_legY;
	private float base_legZ;
	private float base_headY;
	
	public LOTRModelBiped()
	{
		super();
	}
	
	public LOTRModelBiped(float f)
	{
		super(f);
	}
	
	public LOTRModelBiped(float f, float f1, int width, int height)
	{
		super(f, f1, width, height);
	}
	
	private void setupModelBiped()
	{
		base_bodyRotateX = bipedBody.rotateAngleX;
		base_armX = Math.abs(bipedRightArm.rotationPointX);
		base_legY = bipedRightLeg.rotationPointY;
		base_legZ = bipedRightLeg.rotationPointZ;
		base_headY = bipedHead.rotationPointY;
	}
	
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		if (!setup)
		{
			setupModelBiped();
			setup = true;
		}
		
		bipedHead.rotateAngleY = f3 / (180F / (float)Math.PI);
        bipedHead.rotateAngleX = f4 / (180F / (float)Math.PI);
        bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
        bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2F * f1 * 0.5F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2F * f1 * 0.5F;
        bipedRightArm.rotateAngleZ = 0F;
        bipedLeftArm.rotateAngleZ = 0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0F;
        bipedLeftLeg.rotateAngleY = 0F;

        if (isRiding)
        {
            bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
            bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
            bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
            bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
        }

        if (heldItemLeft != 0)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)heldItemLeft;
        }

        if (heldItemRight != 0)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)heldItemRight;
        }

        bipedRightArm.rotateAngleY = 0F;
        bipedLeftArm.rotateAngleY = 0F;

        if (onGround > -9990F)
        {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2F) * 0.2F;
            bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * base_armX;
            bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * base_armX;
            bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * base_armX;
            bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * base_armX;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1F - f6;
            float f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX = (float)((double)bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * -0.4F;
        }

        if (isSneak)
        {
            bipedBody.rotateAngleX = base_bodyRotateX + 0.5F;
            bipedRightArm.rotateAngleX += 0.4F;
            bipedLeftArm.rotateAngleX += 0.4F;
            bipedRightLeg.rotationPointZ = base_legZ + 4F;
            bipedLeftLeg.rotationPointZ = base_legZ + 4F;
            bipedRightLeg.rotationPointY = base_legY - 3F;
            bipedLeftLeg.rotationPointY = base_legY - 3F;
            bipedHead.rotationPointY = base_headY + 1F;
            bipedHeadwear.rotationPointY = base_headY + 1F;
        }
        else
        {
            bipedBody.rotateAngleX = base_bodyRotateX;
            bipedRightLeg.rotationPointZ = base_legZ + 0.1F;
            bipedLeftLeg.rotationPointZ = base_legZ + 0.1F;
            bipedRightLeg.rotationPointY = base_legY;
            bipedLeftLeg.rotationPointY = base_legY;
            bipedHead.rotationPointY = base_headY;
            bipedHeadwear.rotationPointY = base_headY;
        }

        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;

        if (aimedBow)
        {
            float f6 = 0F;
            float f7 = 0F;
            bipedRightArm.rotateAngleZ = 0F;
            bipedLeftArm.rotateAngleZ = 0F;
            bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + bipedHead.rotateAngleY;
            bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + bipedHead.rotateAngleY + 0.4F;
            bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + bipedHead.rotateAngleX;
            bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + bipedHead.rotateAngleX;
            bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
            bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
        }

		if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).isDrunkard())
		{
			float f6 = f2 / 80F;
			float f7 = (f2 + 40F) / 80F;
			f6 *= (float)Math.PI * 2F;
			f7 *= (float)Math.PI * 2F;
			float f8 = MathHelper.sin(f6) * 0.5F;
			float f9 = MathHelper.sin(f7) * 0.5F;
			bipedHead.rotateAngleX += f8;
			bipedHead.rotateAngleY += f9;
			bipedHeadwear.rotateAngleX += f8;
			bipedHeadwear.rotateAngleY += f9;
			bipedRightArm.rotateAngleX = -60F / (180F / (float)Math.PI);
		}
	}
}
