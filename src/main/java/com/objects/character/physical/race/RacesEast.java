package com.objects.character.physical.race;

import com.objects.character.physical.GeneManager;
import com.objects.character.physical.genetics.Genes;
import com.objects.character.physical.genetics.SpectrumTrait;
import org.apache.commons.lang3.tuple.Pair;
@SuppressWarnings("unchecked")
public class RacesEast {
    public static final Race ssa_west = Race.build("sub_saharan:west_african","West African","Coastal and inland West Africa — Nigeria, Ghana, Senegal, Mali, Ivory Coast, Guinea, etc.",
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,500),500f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,440),440f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,248),240f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,240),240f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,180),180f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,180),180f),
    Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,220),220f),
    Pair.of(Genes.albinism,.21f),
    Pair.of(Genes.redhead,0.0021f)
    );
    public static final Race ssa_east = Race.build("sub_saharan:east_african","East African/Nilotic","Kenya, Tanzania, Uganda, Sudan, South Sudan — especially tall, lean Nilotic peoples like Dinka, Maasai, Turkana",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,500),500f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,460),460f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,290),290f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,230),230f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,185),185f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,225),225f),
            Pair.of(Genes.albinism,.13f),
            Pair.of(Genes.redhead,0.0021f)
    );
    public static final Race ssa_central = Race.build("sub_saharan:central_african","Central African/Congo","DRC, Republic of Congo, Cameroon, Gabon, CAR — includes Bantu majority and Pygmy (Aka, Baka, Mbuti) populations",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,500),500f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,420),420f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,180),180f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,220),220f),
            Pair.of(Genes.albinism,.21f),
            Pair.of(Genes.redhead,0.0021f)
    );
    public static final Race ssa_south = Race.build("sub_saharan:south_african","Central African/Congo","Zimbabwe, Zambia, Mozambique, South Africa (Zulu, Xhosa, Sotho ancestry, etc.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,500),500f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,400),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,235),235f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,180),180f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,215),215f),
            Pair.of(Genes.albinism,.26f),
            Pair.of(Genes.redhead,0.0021f)
    );
    public static final Race ssa_khoisan = Race.build("sub_saharan:khoisan","Khoisan","Southern Africa — San (Bushmen) and Khoikhoi peoples of Botswana, Namibia, South Africa. One of the oldest genetic lineages on earth.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,490),490f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,320),320f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,215),215f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,210),210f),
            Pair.of(Genes.albinism,0.10f),
            Pair.of(Genes.redhead,0.0021f)
    );
    public static final Race ssa_horn_of_africa = Race.build("sub_saharan:horn_of_africa","Horn of Africa","Somalia, Eritrea, Djibouti, eastern Ethiopia — Cushitic and Afroasiatic peoples with significant ancient Middle Eastern admixture",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,495),495f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,370),370f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,135),135f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,200),200f),
            Pair.of(Genes.albinism,0.10f),
            Pair.of(Genes.redhead,0.00257f)
    );
    public static final Race naf_maghreb = Race.build("northern_africa:northwestern_africa","Northwestern Africa","Morocco, Algeria, Tunisia, Libya. Predominantly Berber/Amazigh ancestry with significant Arab admixture. Some Southern European signal in northern coastal areas.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,420),420f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,280),280f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,185),185f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,180),180f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,185),185f),
            Pair.of(Genes.albinism,0.0605f),
            Pair.of(Genes.redhead,0.0034f)
    );
    public static final Race naf_egypt = Race.build("northern_africa:egypt_nile","Nile River Valley","Egypt and northern Sudan. Ancient Egyptian, Coptic, Arab, and some Sub-Saharan admixture. Genetically distinct from Maghreb — more Levantine influence.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,450),450f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,310),310f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,180),180f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,180),180f),
            Pair.of(Genes.albinism,0.0605f),
            Pair.of(Genes.redhead,0.0010f)
    );
    public static final Race naf_saharan = Race.build("northern_africa:saharan","Nile River Valley","Egypt and northern Sudan. Ancient Egyptian, Coptic, Arab, and some Sub-Saharan admixture. Genetically distinct from Maghreb — more Levantine influence.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,460),460f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,340),340f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,265),265f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,235),235f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,195),195f),
            Pair.of(Genes.albinism,0.0605f),
            Pair.of(Genes.redhead,0.00685f)
    );
    public static final Race mea_levant = Race.build("middle_east:levant","Levant","Syria, Lebanon, Jordan, and Palestine. Ancient Canaanite and Phoenician base with Arab, Crusader, and broader Mediterranean admixture.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,400),400f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,180),180f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,190),190f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,175),175f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_arabian = Race.build("middle_east:arabian","Arabian Peninsula","Saudi Arabia, Yemen, UAE, Oman, and Kuwait. Core Arab ancestry with ancient trade route admixture along coastal areas.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,460),460f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,290),290f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,172),172f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_mesopotamia = Race.build("middle_east:mesopotamia","Mesopotamia and Persia","Iraq and Iran. Ancient Persian and Mesopotamian ancestry, distinct from Arab populations with significant Indo-European linguistic and genetic heritage.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,390),390f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,235),235f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,255),255f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,135),135f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,178),178f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,185),185f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,170),170f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_anatolia = Race.build("middle_east:anatolia","Anatolia","Modern Turkey. Among the most admixed populations on earth — ancient Anatolian, Greek, Central Asian Turkic, Armenian, and Arab layers.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,370),370f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,253),253f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,172),172f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,168),168f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_caucasus = Race.build("middle_east:caucasus","Caucasus","Armenia, Georgia, and Azerbaijan. Ancient Caucasian ancestry sitting genetically between Middle East and Europe, distinct from both.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,360),360f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,195),195f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,256),256f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,182),182f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,178),178f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,143),143f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,165),165f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_jewish_ashkenazi = Race.build("middle_east:jewish_ashkenazi","Ashkenazi Jewish","Jewish diaspora of Central and Eastern Europe. Middle Eastern origin with significant European admixture and strong founder effects from centuries of endogamy.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,340),340f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,257),257f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,178),178f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,160),160f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_jewish_sephardic = Race.build("middle_east:jewish_sephardic","Sephardic Jewish","Jewish diaspora of Iberia and North Africa, expelled from Spain in 1492. Middle Eastern origin with Mediterranean admixture, distinct from Ashkenazi.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,390),390f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,178),178f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,165),165f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race mea_jewish_mizrahi = Race.build("middle_east:jewish_mizrahi","Mizrahi Jewish","Jewish communities of Iraq, Iran, Yemen, and broader Middle East. Closest genetically to ancient Levantine ancestry with minimal European admixture.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,430),430f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,255),255f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,249),249f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,177),177f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,147),147f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,168),168f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race cas_kazakh = Race.build("central_asia:kazakh","Kazakhstan and Kyrgyzstan","Kazakhstan and Kyrgyzstan. Turkic steppe peoples with strong Mongolic admixture from the Mongol expansion. East Asian features are prominent — notably more so than Uzbek or Tajik neighbors to the south.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,478),478f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,65),65f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,208),208f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,178),178f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cas_uzbek = Race.build("central_asia:uzbek","Uzbekistan and Tajikistan","Uzbekistan and Tajikistan. Iranian and Persian base with Turkic and Mongolic overlay — the Silk Road crossroads. Noticeably more Middle Eastern in character than Kazakh neighbors, with Tajiks especially being nearly Iranian in genetic profile.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,430),430f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,225),225f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,255),255f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,182),182f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,205),205f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,168),168f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cas_turkmen = Race.build("central_asia:turkmen","Turkmenistan","Turkmenistan. Turkic dominant with Iranian substrate and some Mongolic admixture — sits between Kazakh and Uzbek on most axes but with its own distinct character from centuries of relative geographic isolation.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,452),452f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,256),256f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,142),142f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,154),154f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,172),172f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cas_pashtun = Race.build("central_asia:pashtun","Pashtun Afghanistan","Pashtun peoples of Afghanistan and northwestern Pakistan. Iranian base with some Steppe ancestry — tall, notably light eyed by Central Asian standards, with a distinctly Middle Eastern character on most axes.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,405),405f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,228),228f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,262),262f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,188),188f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,168),168f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cas_hazara = Race.build("central_asia:hazara","Hazara Afghanistan","Hazara peoples of central Afghanistan. Descended largely from Mongol garrisons left after the Mongol invasion — among the most visibly East Asian influenced populations in South/Central Asia despite being landlocked deep in Afghanistan.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,475),475f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,58),58f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,205),205f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,235),235f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,180),180f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race sas_north_india = Race.build("south_asia:north_india","North India","The Indo-Gangetic plain — Uttar Pradesh, Bihar, Punjab, Rajasthan. Broad Indo-Aryan ancestry with some Central Asian admixture, especially in the northwest.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,430),430f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,260),260f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,120),120f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,165),165f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sas_south_india = Race.build("south_asia:south_india","South India","Tamil Nadu, Kerala, Karnataka, Andhra Pradesh. Dravidian ancestry — one of the oldest population layers in South Asia, distinctly darker and genetically separate from North India.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,480),480f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,390),390f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,160),160f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sas_bengal = Race.build("south_asia:bengal","Bengal and East India","West Bengal, Bangladesh, and Odisha. Indo-Aryan base with distinct eastern character — slightly more Southeast Asian signal than the Indo-Gangetic plain.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,460),460f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,310),310f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,235),235f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,162),162f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sas_pakistan = Race.build("south_asia:pakistan","Pakistan and Northwest","Pakistan and the Afghan border zone. Indo-Aryan base with significant Central Asian and Iranian admixture — bridges South Asia and the Middle East.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,400),400f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,230),230f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,255),255f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,120),120f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,195),195f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,168),168f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sas_sri_lanka = Race.build("south_asia:sri_lanka","Sri Lanka","Island nation off southern India. Mix of Sinhalese and Tamil ancestry with some Southeast Asian signal from historical trade contact and island isolation effects.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,475),475f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,360),360f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,242),242f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,160),160f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sas_himalayan = Race.build("south_asia:himalayan","Nepal and Himalayan","Nepal, Bhutan, and highland Himalayan communities. South Asian base with significant Tibetan and East Asian admixture — bridges South and East Asia on several traits.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,480),480f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,270),270f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,80),80f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,230),230f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,175),175f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race eas_han = Race.build("east_asia:han","Han Chinese","The dominant ethnic group of China, spanning a vast territory. Remarkably consistent traits across a huge population with some north-south variation averaged here.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,490),490f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,40),40f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,200),200f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eas_japanese = Race.build("east_asia:japanese","Japan","The Japanese archipelago. Mix of ancient Jomon hunter-gatherer ancestry and Yayoi agricultural migrants from the continent — subtly distinct from Han Chinese.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,490),490f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,45),45f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,215),215f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,195),195f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eas_korean = Race.build("east_asia:korean","Korea","Korean peninsula. Sits genetically between Han Chinese and Japanese, with its own distinct ancestry layer — closer to northern Han than to Japanese.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,490),490f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,205),205f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,42),42f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,218),218f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,198),198f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eas_mongolian = Race.build("east_asia:mongolian","Mongolia and Steppe","Mongolia and the Central Asian steppe. Turkic and Mongolic ancestry — taller and darker on average than East Asian neighbors, with more Central Asian character.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,485),485f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,240),240f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,262),262f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,50),50f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,225),225f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,195),195f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eas_tibetan = Race.build("east_asia:tibetan","Tibet","The Tibetan plateau. High altitude adaptation has produced real physiological differences — slightly darker skin from UV exposure ancestry, shorter stature, distinct from both Han and Himalayan South Asian groups.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,485),485f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,236),236f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,55),55f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,246),246f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,190),190f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eas_cantonese = Race.build("east_asia:cantonese","Southern China","Guangdong, Fujian, and coastal southern China. Han base with meaningful Southeast Asian admixture — slightly darker, slightly shorter, and marginally more curl than northern Han.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,488),488f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,228),228f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,243),243f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,55),55f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,205),205f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,195),195f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race sea_indochina = Race.build("southeast_asia:indochina","Mainland Southeast Asia","Vietnam, Thailand, Cambodia, Laos, and Myanmar. Mix of Sino-Tibetan and Austroasiatic ancestry — closer to Han Chinese than island Southeast Asia but distinctly its own population layer.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,482),482f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,50),50f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,200),200f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,190),190f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sea_filipino = Race.build("southeast_asia:filipino","Philippines","The Philippine archipelago. Austronesian base with Spanish colonial and southern Chinese admixture. Island isolation has produced a distinct population character.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,484),484f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,236),236f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,75),75f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,187),187f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sea_malay = Race.build("southeast_asia:malay","Indonesia and Malaysia","The Malay archipelago — Indonesia, Malaysia, Brunei. Austronesian dominant across a vast island chain with some Indian and Chinese admixture in coastal trading areas.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,484),484f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,278),278f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,233),233f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,80),80f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,188),188f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,185),185f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sea_papuan = Race.build("southeast_asia:papuan","Papua New Guinea","Papua New Guinea and West Papua. One of the most genetically distinct populations on earth with very ancient divergence from other human groups. Despite Pacific geography, traits on several axes resemble Sub-Saharan Africa due to shared ancestral retention.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,494),494f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,415),415f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,244),244f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,228),228f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,185),185f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sea_negrito = Race.build("southeast_asia:negrito","Andamanese and Negrito","Andaman Islands, Philippine Aeta, Malaysian Orang Asli. Ancient pre-Austronesian hunter-gatherer populations — among the earliest diverging human lineages outside Africa. Very short stature, tightly coiled hair, dark skin despite Asian geography.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,495),495f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,398),398f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,244),244f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,251),251f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,167),167f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,183),183f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race oce_aboriginal = Race.build("oceania:aboriginal","Australian Aboriginal","Indigenous Australians — one of the oldest continuous cultures on earth with extremely ancient divergence from other human populations, predating the Austronesian expansion entirely.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,495),495f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,410),410f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,185),185f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_maori = Race.build("oceania:maori","Māori","Indigenous New Zealanders — Eastern Polynesian ancestry, arriving in Aotearoa around 1300 CE. Tend toward tall, robust builds compared to other Polynesian groups.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,482),482f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,310),310f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,100),100f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,195),195f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,185),185f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_hawaiian = Race.build("oceania:hawaiian","Native Hawaiian","Pure Native Hawaiian ancestry — Eastern Polynesian, closely related to Māori. Hawaii was one of the last major island groups settled by Polynesians around 1000 CE.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,482),482f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,305),305f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,265),265f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,248),248f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,183),183f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_hawaiian_mixed = Race.build("oceania:hawaiian_mixed","Mixed Native Hawaiian","Hawaii's majority population — significant admixture with Japanese, Chinese, Filipino, and White ancestry reflecting Hawaii's unique colonial and immigration history.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,440),440f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,255),255f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,80),80f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,195),195f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,225),225f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,135),135f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,180),180f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_samoan = Race.build("oceania:samoan","Samoa","Independent Samoa and American Samoa. Among the largest-bodied Polynesian populations — notably tall and robust on average. Western Polynesian ancestry.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,484),484f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,320),320f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,275),275f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,98),98f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,195),195f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,182),182f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_tongan = Race.build("oceania:tongan","Tonga","The Kingdom of Tonga. Western Polynesian ancestry closely related to Samoan, similarly large and robust builds, with subtle anthropometric differences from centuries of island isolation.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,484),484f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,315),315f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,272),272f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,193),193f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,182),182f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_melanesian = Race.build("oceania:melanesian","Melanesia","Fiji, Vanuatu, Solomon Islands, and New Caledonia. Distinct from both Polynesian and Papuan ancestry — darker skin and curlier hair than Polynesian neighbors, related to but not identical with Papuans.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,492),492f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,390),390f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,242),242f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,252),252f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,183),183f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_micronesian = Race.build("oceania:micronesian","Micronesia","Guam, Palau, Marshall Islands, and Federated States of Micronesia. Mix of Southeast Asian and Polynesian ancestry — lighter and straighter-haired than Melanesians, distinct from both.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,484),484f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,290),290f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,85),85f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,185),185f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,183),183f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_australian_white = Race.build("oceania:australian_white","Australian White","Australians of European descent — overwhelmingly British Isles founder population with strong Irish component. Subtle founder effects from colonial-era bottleneck.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,280),280f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,262),262f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,100),100f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_pakeha = Race.build("oceania:pakeha","Pākehā","New Zealanders of European descent — similar British Isles founder stock to Australian White but with a slightly different colonial demographic history and settlement pattern.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,275),275f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,115),115f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,260),260f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,100),100f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,115),115f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race oce_hawaiian_white = Race.build("oceania:hawaiian_white","Hawaiian White","White Americans settled in Hawaii — more mainland American mixed character than Australian or NZ White, reflecting broader continental US demographic origins rather than a primarily British Isles founder population.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,260),260f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,260),260f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,105),105f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,130),130f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,150),150f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_italian_north = Race.build("southern_europe:italian_north","Northern Italy","Lombardy, Piedmont, Veneto, and Friuli. More Central European admixture than the south — lighter skin, more hair color variation, taller on average. Genetically closer to French and German neighbors than to Sicilians.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,365),365f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,172),172f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,260),260f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,115),115f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,178),178f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,152),152f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_italian_south = Race.build("southern_europe:italian_south","Southern Italy and Sicily","Campania, Calabria, Sicily, and Sardinia. Darker and shorter on average than northern Italians, with historical Greek, North African, and Arab admixture producing a distinctly Mediterranean character.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,420),420f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,215),215f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,122),122f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,212),212f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,143),143f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,155),155f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_iberian = Race.build("southern_europe:iberian","Iberian Peninsula","Spain and Portugal. Dark hair and olive skin dominant with meaningful variation — northern Iberia especially Galicia has more Celtic influence with lighter traits. Some North African Moorish admixture in the south.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,395),395f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,192),192f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,152),152f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_greek = Race.build("southern_europe:greek","Greece and Cyprus","Greece and Cyprus. Remarkable ancient population continuity — modern Greeks show strong genetic affinity with ancient Mycenaeans. Dark hair and olive skin dominant, with Cyprus showing some additional Levantine admixture.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,428),428f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,220),220f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,250),250f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,170),170f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,218),218f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,143),143f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,155),155f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_balkan = Race.build("southern_europe:balkan","Balkans","Albania and former Yugoslavia — Serbia, Croatia, Bosnia, Montenegro, Slovenia, North Macedonia, Kosovo. Home to the Dinaric Alps population, among the tallest in the world. Mix of Illyrian, Slavic, and Byzantine ancestry.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,375),375f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,185),185f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,274),274f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,115),115f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,165),165f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,175),175f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,153),153f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race seu_maltese = Race.build("southern_europe:maltese","Malta","The Maltese archipelago. Unique Semitic-rooted language with Italian and Norman overlay — genetically sits between Southern Italian and North African, with the strongest Arabic genetic signal of any European population.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,445),445f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,238),238f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,242),242f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,172),172f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,228),228f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,157),157f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race cwe_english = Race.build("central_western_europe:english","England and Wales","England and Wales. Anglo-Saxon and Norman base with older Brittonic Celtic substrate. Highly varied hair and eye color by European standards — one of the more internally variable populations on the continent.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,290),290f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,105),105f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cwe_scottish = Race.build("central_western_europe:scottish","Scotland","Scotland. Gaelic Celtic and Norse ancestry mix. Highest per capita red hair rate on earth — a genuine and well documented genetic signal, not a stereotype. Very pale skin, notably high rates of blue and green eyes.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,112),112f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,108),108f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,122),122f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,136),136f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,145),145f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cwe_irish = Race.build("central_western_europe:irish","Ireland","Ireland. Gaelic Celtic ancestry with very high red hair rates — second only to Scotland globally. Notably dark brown hair is actually common alongside red, making Ireland unusual in having both extremes well represented.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,298),298f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,110),110f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,110),110f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,153),153f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,136),136f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,145),145f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cwe_french = Race.build("central_western_europe:french","France","France. Celtic Gaulish base with Frankish Germanic and Mediterranean influences. More internal north-south variation than the numbers suggest — Brittany in the northwest reads almost like British Isles while Provence reads almost Mediterranean.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,320),320f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,108),108f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race cwe_german = Race.build("central_western_europe:german","Germany and Low Countries","Germany, Netherlands, Belgium, Austria, and Luxembourg. Germanic ancestry dominant — one of the more blonde-skewed populations outside Scandinavia, notably tall, high rates of blue and gray eyes.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,245),245f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,265),265f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,100),100f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,118),118f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race sca_nordic = Race.build("scandinavia:nordic","Nordic","Norway, Sweden, and Denmark. Among the blondest and palest populations on earth — strong depigmentation selection in northern latitudes. Tall, high rates of blue eyes, characteristically low melanin across all traits.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,270),270f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,88),88f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,145),145f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sca_finnish = Race.build("scandinavia:finnish","Finland","Finland. Uralic ancestry rather than Germanic — genetically distinct from Scandinavian neighbors despite geographic proximity. Famous founder effect producing unusually high rates of several rare recessive conditions. Slightly darker and shorter than Nordic average.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,205),205f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,102),102f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,262),262f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,92),92f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,152),152f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,98),98f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,134),134f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,147),147f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race sca_icelandic = Race.build("scandinavia:icelandic","Iceland","Iceland. Descended from a tiny Norse and Celtic founding population in the 9th century — one of the most extreme founder effects of any European population. High genetic homogeneity, slightly more Celtic red hair signal than mainland Nordic.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,210),210f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,98),98f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,267),267f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,98),98f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,92),92f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,133),133f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,146),146f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static final Race eeu_polish = Race.build("eastern_europe:polish","Poland and West Slavic","Poland, Czech Republic, and Slovakia. West Slavic ancestry — sits genetically between Germanic and East Slavic populations. Lighter than East Slavic on average, notably high rates of gray eyes.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,128),128f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,158),158f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,125),125f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,140),140f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,135),135f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,148),148f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eeu_east_slavic = Race.build("eastern_europe:east_slavic","East Slavic","Russia, Ukraine, and Belarus. The largest population cluster in Europe by landmass. Slightly darker hair on average than West Slavic neighbors, wide internal variation especially across Russia's vast geography.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,295),295f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,265),265f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,98),98f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,132),132f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,142),142f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,136),136f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,150),150f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eeu_baltic = Race.build("eastern_europe:baltic","Baltic States","Estonia, Latvia, and Lithuania. Among the most genetically conservative populations in Europe — Baltic languages are the oldest surviving Indo-European languages and the genetics reflect ancient continuity. Very high blue eye and light hair rates, rivaling Scandinavia.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,198),198f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,105),105f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,268),268f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,92),92f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,155),155f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,95),95f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,145),145f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,133),133f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,147),147f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eeu_romanian = Race.build("eastern_europe:romanian","Romania and Moldova","Romania and Moldova. Fascinating mix of Latin linguistic heritage with Slavic, Dacian, and some Turkic genetic ancestry. Darker than Slavic neighbors, bridging Eastern and Southern Europe.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,358),358f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,258),258f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,108),108f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,162),162f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,168),168f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,150),150f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,150),150f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );

    public static final Race eeu_hungarian = Race.build("eastern_europe:hungarian","Hungary","Hungary. Uralic origin like Finland — Magyar ancestry from Central Asian steppe migrations in the 9th century, now heavily admixed with surrounding Slavic and Germanic populations but retaining subtle distinct character.",
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Blonde_Black,318),318f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Skin_Color,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Height,262),262f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Curly,100),100f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Thickness,160),160f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Blue_Brown,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Eye_Color_Gray_Green,148),148f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Hair_Gray_Age,138),138f),
            Pair.of(GeneManager.getGeneSpectrum(SpectrumTrait.Type.Male_Baldness_Age,150),150f),
            Pair.of(Genes.albinism,0f),
            Pair.of(Genes.redhead,0f)
    );
    public static void init(){

    }
}
