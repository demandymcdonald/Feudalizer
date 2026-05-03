package com.objects.organization.government.tenets.economy;

import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.flag.FlagTenet;
import com.objects.culture.tenet.group.groups.GovernmentGroups;

import java.util.Set;

public class Flags {





    public static class FreeMarketFlag extends FlagTenet{

        public FreeMarketFlag(InstanceType type, String id) {
            super(type, id);
        }

        public FreeMarketFlag(InstanceType type, TenetReference parent) {
            super(type, parent, GovernmentGroups.ECONOMIC_INTERVENTION, new PoliticalCompass(-50,15,35,15), "free_market", "Free Market Economy", "The government has little to no intervention. Prices are set entirely by supply and demand, and private individuals/businesses make all economic decisions. Pure free markets don't really exist in practice — it's more of a theoretical ideal.");
        }

        @Override
        public void incompatibleFlags(Set<Class<? extends FlagTenet>> flags) {
            super.incompatibleFlags(flags);
            flags.add(MixedMarketFlag.class);
            flags.add(TraditionalEconomyFlag.class);
            flags.add(CommandEconomyFlag.class);
        }

        @Override
        public FlagTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new FreeMarketFlag(type, id);
        }

        @Override
        public void getConditions(Set<CultureCondition<?, ?>> conditions) {

        }
    }
    public static class MixedMarketFlag extends FlagTenet{

        public MixedMarketFlag(InstanceType type, String id) {
            super(type, id);
        }
        public MixedMarketFlag(InstanceType type, TenetReference parent) {
            super(type, parent, GovernmentGroups.ECONOMIC_INTERVENTION, new PoliticalCompass(-5,5,0,10), "mixed_market", "Mixed Markets", "The most common system in the real world (US, UK, most of Europe, etc.). A blend of free market activity and government regulation/intervention. Governments may provide public goods, regulate industries, set minimum wages, collect taxes, and run programs like healthcare or social security, while most production is still privately owned.");
        }

        @Override
        public FlagTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new MixedMarketFlag(type, id);
        }
        @Override
        public void incompatibleFlags(Set<Class<? extends FlagTenet>> flags) {
            super.incompatibleFlags(flags);
            flags.add(FreeMarketFlag.class);
            flags.add(TraditionalEconomyFlag.class);
            flags.add(CommandEconomyFlag.class);
        }
        @Override
        public void getConditions(Set<CultureCondition<?, ?>> conditions) {

        }
    }
    public static class CommandEconomyFlag extends FlagTenet{

        public CommandEconomyFlag(InstanceType type, String id) {
            super(type, id);
        }
        public CommandEconomyFlag(InstanceType type, TenetReference parent) {
            super(type, parent, GovernmentGroups.ECONOMIC_INTERVENTION, new PoliticalCompass(60,-20,-5,40), "command_economy", "Command Economy", "The government owns the means of production and makes most or all economic decisions — what gets produced, in what quantities, and at what prices. The Soviet Union is the classic historical example. North Korea is a modern one. These tend to struggle with efficiency and innovation.");
        }

        @Override
        public FlagTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new CommandEconomyFlag(type, id);
        }
        @Override
        public void incompatibleFlags(Set<Class<? extends FlagTenet>> flags) {
            super.incompatibleFlags(flags);
            flags.add(FreeMarketFlag.class);
            flags.add(TraditionalEconomyFlag.class);
            flags.add(MixedMarketFlag.class);
        }
        @Override
        public void getConditions(Set<CultureCondition<?, ?>> conditions) {

        }
    }
    public static class TraditionalEconomyFlag extends FlagTenet{

        public TraditionalEconomyFlag(InstanceType type, String id) {
            super(type, id);
        }
        public TraditionalEconomyFlag(InstanceType type, TenetReference parent) {
            super(type, parent, GovernmentGroups.ECONOMIC_INTERVENTION, new PoliticalCompass(0,0,-40,-100), "traditional_economy", "Traditional Market", "Based on customs, history, and inherited roles. Economic decisions are guided by tradition rather than market forces or central planning. Common in subsistence farming communities or indigenous societies. Things are produced the way they've always been produced.");
        }
        @Override
        public void incompatibleFlags(Set<Class<? extends FlagTenet>> flags) {
            super.incompatibleFlags(flags);
            flags.add(FreeMarketFlag.class);
            flags.add(MixedMarketFlag.class);
            flags.add(CommandEconomyFlag.class);
        }
        @Override
        public FlagTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new TraditionalEconomyFlag(type, id);
        }

        @Override
        public void getConditions(Set<CultureCondition<?, ?>> conditions) {

        }
    }
}
