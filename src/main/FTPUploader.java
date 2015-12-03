/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.Window;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author janabelmann
 */
public class FTPUploader{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            if(System.getProperty("os.name").startsWith("Windows")){
                UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel" );  
                MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            }
            else if("Mac OS X".equals(System.getProperty("os.name"))) {
                String png_upload = "89504E470D0A1A0A0000000D4948445200000080000000800806000000C33E61CB00000CE34944415478DAED9D7F8C14571DC0BFBB7BB7F7BBC0C1F1A377578F2AB9036B3DB497406D2DC43605295A4DB5627A1588942850AE692DFE8136044CAA29A9A6E91FB6359C29FD955A41C182A1452A228DC580F66CEF80C25D0A395289E01DBF0A77B77EBFBB6F706F77767766DE7BF3E6CDBE4FF2327B7BB3B36FF67DE6BD793F27022163FBCE99D5B86961A119430386260C933154B3BFF37102C3390CA730F4B2BF7B307453B86BDEC173AACF512411D511E00513BC1537B7619885E1260C9F92FC9547311CC0F03686B7508843AA7F031EB41300137C026EE66158C0B6631547E92C869D18FE405B14E2B4E2F8B8420B0158B6FE2D0CF7629883A144759C723084610F865730BCAC437111680130E1295B5F0EA9C42F571D1F975CC2F032865FA2086FAB8E4C2E022700263A5DDD94E00F6368551D1F41D07DC24648E50A43AA23934E6004C084A72B9CAEF60E48DDB587915E0C3F8754AE7049756408E502B02BFE3E0C8F4178133E935E0CEB306C569D2328150013FF16DC3C05E1C9EADD4245C32A94E02FAA22A0440056957B125257BE0173020C0FA9A842FA2E0026FE624825BEEAFA7BD0A0F60492A0D3CF2FF54D0076D53F8BE16E3F4F5043B66258E6576EE08B0098F8B7E36613146E8737A4A0FE872528C11BB2BF48BA0098F86B70B30182DB7A1754A876B01625F8A9CC2F9126006BBEA5ABFE1E99275004506BE23259CDCA5204C0C4A7AED76D90EA9D33F043BD8F0B518253A20F2C5C004C7CEA87DF01C5D3A8E317BD18E6A304DD220F2A540096F87F82D4E00B83782807982B52026102B0563DBAF2AB15FC30C504DD0BCC17D57A28440076E5BF0326F1FD822468139113700B60B27D6508290EB8046077FBFBC1DCF0A9A217C36C9EDA816701583D9FAE7C53D5530B5511E77A6D27E011E025488DDC31A887461A2DF2F2414F02B0E6DDC7559FB561143FF4D26CEC5A00D6B143D53DD3B61F2CA8EF60BEDB0E245702B02EDD83607AF5820AF522CE74D395EC56802D60FAF383CE5614E06B4E77762C001BC9B349F5D9191CB1C4E9C8224702B0ACFF08846C185765652394C5C7C399B35A4FEFB38386974D7352143815E07908D900CE192D8FC0D44F7C3BF9FA78DF8BF05EF713AAA3241A1A72DE5E68A78202B04E9EBDAACF4624E9896F1152096E2DD4699457003669833A7942336EDF2EF12D422801956D6DF9269F1412603184E8C62F5FE25B845082BC378439056073F5DE879074F438497C8B63C75F80F70F6F541D6551F462989E6B2E623E0156436A22A3F6B8497C8BC3475F80231F8446820E14E01776FFB0158095FDC721042D7E5E12DF224412F442AA5A98752F904B00AAF23DAF3AD6BCF024BE054970F8E8131089289F48CD4B3B0AB039F3CD5C02507BBFD677FE2212DFA2E7F06638726CA3EE121C42016666BE9975466C5996FDAA63CB83C8C4B7088904B33397ABB11380AA7D8B55C7D42B3212DF22041274A2004BD2DF1875266C98D7BF41BF059992C84C7C0BCD25A0AA605DFAF0B14C01BE0BA929DCDAE147E25B682E01CD337CCEFA2353805DB8B95D750CDDE267E25B682CC11B28C01DD61F5763CFBA7CFB41B3A15E2A12DF425309A82D608AD5559C2E8076757F95896FA1A90457DB04D205D06A98F7F4E60EB8BEE97ED5D148A2A104578791A70B7006341AF173C7DCDD108F0727BADB5E6FC75FB30BA2512D24388B028CA317C9D8B225D70FAA8E951B3E7BC3BA517F97958D87BA0937FBF2DD7D7D7BE1FC85D350521287CACA3A181919823FEE7A14B7FF816BEBCB749180460F1FB204D0BEE76F7CEDE761569B3F35D8D7B6DC0F274EFECDF67FE5E5515D2448F6105A026855FEDB111401084D2448DE075802D0885FD94FDA904A9004203490E0280A302DC29A7F0755C78697A009406820410D0940D3BBDF511D135E822800117009DA223A3600D911540188004BD04E02ACC7176B55C78497200B400454820D11DDFBFF2D822E0041124CB9B60C62B1C048D04902D0322F7354C784171D04204A4B2350DF500E25258190600F094063FF5B54C784175D04200224413709F0218460F8B74E02100191E004099050190351E826001104098C001E102500A15A02238007440A40A894C008E001D10210AA24300278408600840A098C001E902500E1B70446000FC81480A0C4A766E3783C2AFD5C8C001E902D0041CDC5F50DF225300D411EF04300C207094E98A6600FF825002159826ED319E4013F0520244AB0C7B7EEE058AC02A6373F04D5554D39F7B97CF92CFCB3EB577065A8DBF5240B1D049838F106B8E973CBA0A2625CCE7DCE9C39067BF7FD0CAE5CB990F1FB4991A0D3B70121377EFA47D0D850780D631A6FBF75DB9D3076ACBB7BD3A00B108BC561C977764355E58482FB76BDF71B7873F75A9B63089760836F43C266B53D8389E4ECE9329B7EFD25A8AC3E0DD5D531C7C70FBA00D7D4D4A3006F3ADA978E4DDF61876009DA7D1B14EA5680C17327A1A1B11CCACA9C9D68B108400894A0CDB761E16E0518183C996C156BBCAEDCD138BA6212802009A8B1C8E90592831ADF2686781180A8AA8A25C7D115A2D80420E83E9924A8A8705E54A6919A1842AFFC981AE65500A2767C29D4D696E6FD4C310A407048306A6A98F4C9A13C0210940B506E908B621580F028C1A8C9A1D2A787F30A10C5736BC49BC2D252FB32AF9805203C48F0FFE9E184EC0522780520E8AEB7F1BA32DB46A26217807021C1E8052208D9F7012204206AAE89C1A449D93785D4D238A3790DD618E43EC37A60E024ECF9F3FAAC96BA42F82100E15002DB2562A4360889128098501787B163ED1733EBEFFF18CE9F1B96751A9EF14B00A290048944A27DE1FC43598B44495D264EA400444363199497679FE0F07002FAFA2EC248C01CF05300228F0443F81B4DF9EA8243A3978923642E14295A006A08A14622BBA153E70687E0D4A9CB324EC3337E0B40E490C07EA14842E652B1A20520CA2BA2505F6F7F5318B4A240850004FD349326C5A1BA2695B15FBE3CB2ECEB5FF947CEA562A52D162D430062CC9812A89B18CF7A3F6845812A012C264F4E4A70697070A86ED137DEB55F2C9A90353EC08D00D41F7EE9E219C7C71E575B9AEC37C8E4C2859164711004CA2BC6C1AD5F78D4D1BE3204203027E85CF4CD77732F174FC87A60841B018A1D590294C6235F5CB1BC7BD443407D7B648C11C0393204C07B81AED52B7B3E93F5BEDDCE32DA048C00CE9124C01214A033EB7DBB9DD963E3A88BB84954048C00CE112D0026FE090C531F5CD1E3ECB17184E81E42238073440B108DC20F30F16D9F87EBDBA3638D00CE11DC0ED08B9B1998FD5FB4FD7FBE0F8B7C78B411C039220588C560D9AAEFF73C97EBFFBE3D3EDE08E01C814DC15D1866DA95FD57F729741094E016DCEC2DB45F218C00CE1128C05CCCFAF7E4DDC7C9815002AA12DEC713994F36AD8496E6A5027FA6F072E0EFCFC2BEFD7C0FADC6ACFF15CCFA0B8EEF702A00751553B5D0F388A1F3E701C68DB9136A6AEA05FF5CE182069C74F7FC1E1209AE4E8C01BCFAA7E1D5FF51A11D1D4FC013714318C46EDA308257FF0378F53BEAD57535031325D8829BBB79226724900BD6F9B7E34DDF42A7FBBB15808A02EA27E05A50C248208D7ECCFA5B9D64FD16AE5722420968C4D00EE01C3A662410CE3066FD7761D6BFD3CD873C2D458512ACC1CDE3BC313612882312851FAF5ED1B3DEF5E7BC7EA1A861E446027EB0DC7F0DCBFD7BBC7C9647001A3E46CBCB70B7EE1809BC83653EDD93CDC1727FC0D3E779BE1C25A05918347AA889F7448C049EF81005988D89EF6C00A50DDCCB51A204B4C218E504DC53728C04AEA0C1BB733B56F5FC8BE72042D623651250A75135EFB18C048EA0ECFE66DEC427842D48CB3A8DA87A682490CB859292C897577EAFFB2D110713BA22B1290EA42324DB4F47F892D44C02CA099A788F652418052DE93B5F64E21352D62467B5836D60AA88426055BD853C77FB398F2D2BD2AC9D807AA44C631107D4C89348C052AFF5FC42487F2A016B36DE00A6EFC02DC39128ACF3D2BCEB065F1E4BC13A90682C81E94574467F2C064BDD76EC78C1B787D3B0AE642A12CC78823C6096FF3BCCF21F70D3A5CB83EFCF2963238B9E048EE16521956000AFFA479C8EE4118592A715B2DC8024F03CD0344C12D000CE911178D0AFAB3E1DA50FAE65AD874F81C77907BA4B40E3F671B3AAD0D06DA97150FD23B0C92794133C061E1A8F749480A66B6159FF132CEB3BF34DDAF0252EAA7F0C0B36177139860E7029822E1250C263781A13FEE95C73F57C8F93EA0864C272046A3C7A185C140D41968065F51B71BB59F5159F1537D511C8075BAE86720512A2E0C2550193E063BCB97B752401CFAC5ED1C33DB54E168116C082352B9304F742EA0967395B15154B40AD77FB20012FE2EB976435DF8A440B01D26155C8791816B06D567B82CF120CE00DDD2E2CD7B7E3EBD75554E578D04E804CB6ED686DC51FFFB6683442C505F53E269F7C224B022CC7FB301CC07AFB5FF16ADF8BD9BBF4E72DC9447B013279F5B73756E359B56002B55CBA38DCFCDF81A1A98911B81EFF351145A982C283554E61029FC7ED47B83D86DB5EFCDC0778CC1ECCDABB74C8D6DDF03FB80B08F956E873840000000049454E44AE426082";
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
                
                //Class.forName("com.apple.eawt.Application", false, null);
                //com.apple.eawt.Application.getApplication().setDockIconImage(new ImageIcon(DatatypeConverter.parseHexBinary(png_upload)).getImage());      
                
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        
        new Window();
        
    }
}