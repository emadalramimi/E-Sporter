package modele.metier;

import javax.swing.ImageIcon;

/**
 * Énumération de tous les pays (pour le pays d'une équipe)
 */
public enum Pays {
    AFGHANISTAN("Afghanistan", "af"),
    AFRIQUE_DU_SUD("Afrique du Sud", "za"),
    ALGERIE("Algérie", "dz"),
    ALLEMAGNE("Allemagne", "de"),
    ANDORRE("Andorre", "ad"),
    ANGOLA("Angola", "ao"),
    ARABIE_SAOUDITE("Arabie Saoudite", "sa"),
    ARGENTINE("Argentine", "ar"),
    ARMENIE("Arménie", "am"),
    AUSTRALIE("Australie", "au"),
    AUTRICHE("Autriche", "at"),
    AZERBAIDJAN("Azerbaïdjan", "az"),
    BAHAMAS("Bahamas", "bs"),
    BANGLADESH("Bangladesh", "bd"),
    BELGIQUE("Belgique", "be"),
    BENIN("Bénin", "bj"),
    BOLIVIE("Bolivie", "bo"),
    BOSNIE_HERZEGOVINE("Bosnie-Herzégovine", "ba"),
    BRESIL("Brésil", "br"),
    BULGARIE("Bulgarie", "bg"),
    CAMBODGE("Cambodge", "kh"),
    CAMEROUN("Cameroun", "cm"),
    CANADA("Canada", "ca"),
    CHILI("Chili", "cl"),
    CHINE("Chine", "cn"),
    COLOMBIE("Colombie", "co"),
    COREE_DU_NORD("Corée du Nord", "kp"),
    COREE_DU_SUD("Corée du Sud", "kr"),
    COSTA_RICA("Costa Rica", "cr"),
    COTE_DIVOIRE("Côte d'Ivoire", "ci"),
    CROATIE("Croatie", "hr"),
    CUBA("Cuba", "cu"),
    CHYPRE("Chypre", "cy"),
    DANEMARK("Danemark", "dk"),
    EGYPTE("Égypte", "eg"),
    EMIRATS_ARABES_UNIS("Émirats Arabes Unis", "ae"),
    EQUATEUR("Équateur", "ec"),
    ESPAGNE("Espagne", "es"),
    ESTONIE("Estonie", "ee"),
    ETATS_UNIS("États-Unis", "us"),
    FINLANDE("Finlande", "fi"),
    FRANCE("France", "fr"),
    GAMBIE("Gambie", "gm"),
    GEORGIE("Géorgie", "ge"),
    GHANA("Ghana", "gh"),
    GRECE("Grèce", "gr"),
    GROENLAND("Groenland", "gl"),
    GUATEMALA("Guatemala", "gt"),
    GUINEE("Guinée", "gn"),
    GUYANE("Guyane", "gy"),
    HAITI("Haïti", "ht"),
    INDE("Inde", "in"),
    INDONESIE("Indonésie", "id"),
    IRAQ("Iraq", "iq"),
    IRLANDE("Irlande", "ie"),
    ISLANDE("Islande", "is"),
    ISRAEL("Israël", "il"),
    ITALIE("Italie", "it"),
    JAMAIQUE("Jamaïque", "jm"),
    JAPON("Japon", "jp"),
    JORDANIE("Jordanie", "jo"),
    KAZAKHSTAN("Kazakhstan", "kz"),
    KENYA("Kenya", "ke"),
    KIRGHIZISTAN("Kirghizistan", "kg"),
    LETTONIE("Lettonie", "lv"),
    LIBAN("Liban", "lb"),
    LIBERIA("Libéria", "lr"),
    LIBYE("Libye", "ly"),
    LITUANIE("Lituanie", "lt"),
    LUXEMBOURG("Luxembourg", "lu"),
    MADAGASCAR("Madagascar", "mg"),
    MALAISIE("Malaisie", "my"),
    MALDIVES("Maldives", "mv"),
    MALI("Mali", "ml"),
    MALTE("Malte", "mt"),
    MAROC("Maroc", "ma"),
    MARTINIQUE("Martinique", "mq"),
    MAURITANIE("Mauritanie", "mr"),
    MAURICE("Maurice", "mu"),
    MEXIQUE("Mexique", "mx"),
    MONACO("Monaco", "mc"),
    MONGOLIE("Mongolie", "mn"),
    MOZAMBIQUE("Mozambique", "mz"),
    NAMIBIE("Namibie", "na"),
    NEPAL("Népal", "np"),
    NIGER("Niger", "ne"),
    NIGERIA("Nigéria", "ng"),
    NORVEGE("Norvège", "no"),
    NOUVELLE_CALEDONIE("Nouvelle-Calédonie", "nc"),
    NOUVELLE_ZELANDE("Nouvelle-Zélande", "nz"),
    OMAN("Oman", "om"),
    OUGANDA("Ouganda", "ug"),
    PAKISTAN("Pakistan", "pk"),
    PANAMA("Panama", "pa"),
    PARAGUAY("Paraguay", "py"),
    PAYS_BAS("Pays-Bas", "nl"),
    PALAOS("Palaos", "pw"),
    PALESTINE("Palestine", "ps"),
    PHILIPPINES("Philippines", "ph"),
    POLOGNE("Pologne", "pl"),
    PORTUGAL("Portugal", "pt"),
    PORTO_RICO("Porto Rico", "pr"),
    QATAR("Qatar", "qa"),
    ROUMANIE("Roumanie", "ro"),
    RUSSIE("Russie", "ru"),
    RWANDA("Rwanda", "rw"),
    SENEGAL("Sénégal", "sn"),
    SERBIE_ET_MONTENEGRO("Serbie-et-Monténégro", "cs"),
    SLOVAQUIE("Slovaquie", "sk"),
    SLOVENIE("Slovénie", "si"),
    SOMALIE("Somalie", "so"),
    SOUDAN("Soudan", "sd"),
    SRI_LANKA("Sri Lanka", "lk"),
    SUEDE("Suède", "se"),
    SUISSE("Suisse", "ch"),
    SURINAME("Suriname", "sr"),
    SYRIE("Syrie", "sy"),
    TADJIKISTAN("Tadjikistan", "tj"),
    TAIWAN("Taïwan", "tw"),
    TANZANIE("Tanzanie", "tz"),
    TCHAD("Tchad", "td"),
    THAILANDE("Thaïlande", "th"),
    TOGO("Togo", "tg"),
    TONGA("Tonga", "to"),
    TRINITE_ET_TOBAGO("Trinité-et-Tobago", "tt"),
    TUNISIE("Tunisie", "tn"),
    TURQUIE("Turquie", "tr"),
    TURKMENISTAN("Turkménistan", "tm"),
    TUVALU("Tuvalu", "tv"),
    UKRAINE("Ukraine", "ua"),
    URUGUAY("Uruguay", "uy"),
    OUZBEKISTAN("Ouzbékistan", "uz"),
    VANUATU("Vanuatu", "vu"),
    VENEZUELA("Venezuela", "ve"),
    VIET_NAM("Viet Nam", "vn"),
    YEMEN("Yémen", "ye"),
    ZAMBIE("Zambie", "zm"),
    ZIMBABWE("Zimbabwe", "zw");

    private final String nomPays;
    private final String codePays;

    /**
     * Construit un pays
     * @param nomPays
     */
    Pays(String nomPays, String codePays) {
        this.nomPays = nomPays;
        this.codePays = codePays.toLowerCase();
    }

    /**
     * @return le nom du pays
     */
    public String getNomPays() {
        return nomPays;
    }

    /**
     * @return le code du pays
     */
    public String getCodePays() {
        return codePays;
    }
    
    /**
     * @return le drapeau du pays
     */
    public ImageIcon getDrapeauPays() {
    	return new ImageIcon(Pays.class.getResource("/images/pays/" + this.codePays + ".png"));
    }

    /**
     * @return une liste du nom de tous les pays
     */
    public static String[] getTout() {
        Pays[] pays = Pays.values();
        String[] nomsPays = new String[pays.length];
        for (int i = 0; i < pays.length; i++) {
            nomsPays[i] = pays[i].getNomPays();
        }
        return nomsPays;
    }
    
    /**
     * @param nom
     * @return un Pays à partir de son nom
     */
    public static Pays valueOfNom(String nom) {
        for (Pays pays : values()) {
            if (pays.getNomPays().equalsIgnoreCase(nom)) {
                return pays;
            }
        }
        throw new IllegalArgumentException("Pays avec le nom '" + nom + "' non trouvé.");
    }
}
