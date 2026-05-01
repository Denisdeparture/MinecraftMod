package com.ctfow.createthefactoryofwar.item;

import com.ctfow.createthefactoryofwar.ColorRegistry;
import com.ctfow.createthefactoryofwar.SpecialTextures;
import com.simibubi.create.content.contraptions.chassis.AbstractChassisBlock;
import com.simibubi.create.content.contraptions.glue.SuperGlueItem;

import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class CustomClue extends Item {
    @SubscribeEvent
    public static void glue(PlayerInteractEvent.RightClickBlock event) {
        var hit = event.getHitVec();
        if (hit != null) {
            BlockState blockState = event.getLevel()
                    .getBlockState(hit.getBlockPos());
            Outliner.getInstance().showAABB("CustomGlue", new AABB(hit.getBlockPos()))
                    .colored(ColorRegistry.Red)
                    .withFaceTexture(new SpecialTextures("glue_textures"))
                    .disableLineNormals()
                    .lineWidth(1 / 16f);
            if (blockState.getBlock() instanceof AbstractChassisBlock cb)
                if (cb.getGlueableSide(blockState, event.getFace()) != null)
                    return;
        }
        if (event.getItemStack().getItem() instanceof SuperGlueItem)
            event.setUseBlock(TriState.FALSE);
    }

    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(Level world, BlockPos pos, Direction direction, boolean fullBlock) {
        Vec3 vec = Vec3.atLowerCornerOf(direction.getNormal());
        Vec3 plane = VecHelper.axisAlingedPlaneOf(vec);
        Vec3 facePos = VecHelper.getCenterOf(pos)
                .add(vec.scale(.5f));

        float distance = fullBlock ? 1f : .25f + .25f * (world.random.nextFloat() - .5f);
        plane = plane.scale(distance);
        ItemStack stack = new ItemStack(Items.SLIME_BALL);

        for (int i = fullBlock ? 40 : 15; i > 0; i--) {
            Vec3 offset = VecHelper.rotate(plane, 360 * world.random.nextFloat(), direction.getAxis());
            Vec3 motion = offset.normalize()
                    .scale(1 / 16f);
            if (fullBlock)
                offset = new Vec3(Mth.clamp(offset.x, -.5, .5), Mth.clamp(offset.y, -.5, .5),
                        Mth.clamp(offset.z, -.5, .5));
            Vec3 particlePos = facePos.add(offset);
            world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), particlePos.x, particlePos.y,
                    particlePos.z, motion.x, motion.y, motion.z);
        }
    }

    public CustomClue(Properties properties) {
        super(properties);
    }
}
