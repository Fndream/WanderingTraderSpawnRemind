package com.github.fndream.wanderingtraderspawnremind.mixin;

import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(WanderingTraderManager.class)
public class WanderingTraderManagerMixin {
//    @Shadow
//    private int spawnTimer;
//    @Shadow
//    private int spawnDelay;
//    @Shadow
//    private int spawnChance;
//
//    @ModifyArg(method = "spawn", at = @At(
//            value = "INVOKE",
//            target = "Ljava/util/Random;nextInt(I)I"),
//            index = 0)
//    private int chance(int x) {
//        return 1;
//    }
//
//    @Inject(method = "spawn", at = @At(value = "RETURN"))
//    private void timerAdd(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
//        spawnChance = 100;
//        if (spawnDelay > 3600) {
//            spawnDelay = 3600;
//        }
//        if (spawnTimer > 20) {
//            spawnTimer = 20;
//        }
//        if (spawnTimer == 20) {
//            System.out.println("delay: " + spawnDelay);
//        }
//    }

    @Inject(method = "trySpawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/WanderingTraderEntity;setPositionTarget(Lnet/minecraft/util/math/BlockPos;I)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void spawnSucceed(ServerWorld world, CallbackInfoReturnable<Boolean> cir, PlayerEntity playerEntity, BlockPos blockPos, int i, PointOfInterestStorage pointOfInterestStorage, Optional optional, BlockPos blockPos2, BlockPos blockPos3, WanderingTraderEntity wanderingTraderEntity) {
        MutableText text = new TranslatableText("text.wandering_trader_spawn_remind.remind", wanderingTraderEntity.getBlockX(), wanderingTraderEntity.getBlockY(), wanderingTraderEntity.getBlockZ()).setStyle(Style.EMPTY.withColor(Formatting.GOLD));
        world.getServer().getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(text, false));
    }
}
