/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import ftp.Ftp;
import ftp.Server;
import gui.Settings;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author janabelmann
 */
public class Window implements ActionListener, DocumentListener, MouseListener{

    private Settings settings;
    private Ftp ftp;
    private JFrame window;
    private Server server;
    
    String[] workflows = { "", "elsa Video 720p", "elsa Video", "Maschinenbau HD", "Maschinenbau", "OhneVorUndAbspan" };
    private JComboBox cb_workflows = new JComboBox(workflows);

    private JButton b_connect = new JButton("Connect");
    private JButton b_fileChooser = new JButton("Datein Auswählen");
    private JButton b_upload = new JButton("Upload");
    private JButton b_test = new JButton("Test");
    
    private JLabel lb_dozent = new JLabel("Dozent:");
    private JLabel lb_titel = new JLabel("Titel:");
    private JLabel lb_beschreibung = new JLabel("Beschreibung:");
    private JLabel lb_datei = new JLabel("");
    private JLabel lb_workflows = new JLabel("Workflow:");
    private JLabel lb_serverIP = new JLabel("IP:");
    private JLabel lb_username = new JLabel("User:");
    private String hex_redLight = "89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA2000000017352474200AECE1CE9000000097048597300000B1300000B1301009A9C180000015969545874584D4C3A636F6D2E61646F62652E786D7000000000003C783A786D706D65746120786D6C6E733A783D2261646F62653A6E733A6D6574612F2220783A786D70746B3D22584D5020436F726520352E342E30223E0A2020203C7264663A52444620786D6C6E733A7264663D22687474703A2F2F7777772E77332E6F72672F313939392F30322F32322D7264662D73796E7461782D6E7323223E0A2020202020203C7264663A4465736372697074696F6E207264663A61626F75743D22220A202020202020202020202020786D6C6E733A746966663D22687474703A2F2F6E732E61646F62652E636F6D2F746966662F312E302F223E0A2020202020202020203C746966663A4F7269656E746174696F6E3E313C2F746966663A4F7269656E746174696F6E3E0A2020202020203C2F7264663A4465736372697074696F6E3E0A2020203C2F7264663A5244463E0A3C2F783A786D706D6574613E0A4CC227590000096B49444154480D8D566B7094E5157EBEDBEE6637D7CD86245C170888114D5210954BA51083C3E5476D2376C6D629E2607FE854E9E8681D0AE2A57564EA1FABD84EC74E6B75B8D529452EA353D4180C49081B4224CA25014392DD4D369BDD24BBDFB5CFBB6419F0C2F4CC9CEFF6BEEF79CE73CE79CFFB49F81E711C479124C9BA6678EEDEBD7B174F9932A5263F3FBF4255D5128E4B966946471289737D7D7DA117B66C696C0E854E67D74CD8B0F9EE64BFDDE82E8905D9099B376F5EDAD4D4F4371A1E302D8B43DF2FFDFDFD83ADADCDEF6DDDBAB536BB9EB3653E4BD9F7ECFD9B1F04A82022BC0C1C3E74E8B7B75555FDAAACACCC6D1806BACF9F47F7D9B376EF850B480C0D8173915B5484C9C1208215157270F66C78DC6E44A351EB54FBA9BFAE58B9621BEDF40A70DA14ACBF93B90015DE09A96C6D6E6EE4BB339E4A390D1F7F6CBCFAF4D3E64380753F606FA23E417D92FA28557C7B9063BF7FE2D7E6D10F3F3412C9A458EA84DADA42AB57AF5E280CF25590BC4AF4EA0307B2399DD7D6D6B6B7BABABAF2DCB973E691DDBB117AE6198513A55B7EF173CC5BFE23940583F0E5E70B7B184B2410BE78115D0D9FE2D49FFF028318F3B76EB557AD5F6FDF346F9ED6D1D1D1B371E3C69F305DADC4B89EB900CD58012635363636F0DD696D69D19FDFB8D12233E777B36638FF7DFB6D67F0FC79C71A1E761CC12891B8A2C98463C7E34EACBBDB6978F75D677B4D9523D66C79F041FBF3C6C6B4B0D5DCDCDC4EFB4181C1D70C965A5F5F9F650A56ED730B162C5872AABDDDFA60E79B8A79BC51CEE5E4555BB662D1BDF7425614186408872590CD96889924A1C0EBC55DB52B91234BD8BFFE01D8278E4B87FEE4689ACB65D4D4D4DCBA7FFFFEE7D7AD5BF790D808BB76ED5294D3A74F4BDBB66D73188E156BD7AEFDA34D8F8EBCF79EADB79F54D28DC7B1F0914770E7BAB5902D13C66014AAAE434EA7AF53299582311C83A31BF017FB61E5E4A0E7DF07E02D2D91FA934994CDAE900B0B0BABA8273FF9E4933304863A51C112BDD93079F26457D3A79F19C98E764D8EC7318964E6CEA9803232022736048DCC98A4890A115485380C1FA0F16E728EACB9503133880B1C198E84317EA6530935371BCB6A6BB5A54B966CE4E783C44CAB7C1072DBA44993564522515CEA6897B5B151584D2D98C9011F3D96C303506C1BCC09A34A40A1D708B743666B8939B6AA2267248120C73B4F7640993A0D973B4E297D9595280E0496DF7FDF7D0B77EDDBF7590678C72BAF2CCECDCB0BF45EEC811909CB72320151B301AAD6730172490032739711993BEE3A60D215F926A82C1CE0A37AE13C8A79171A8F0F438A0D49BD3D3DCE8C8A0ADFF2DADA1F5E050E9496D6282C9CE148C4723BB6AC87C3F073519E578176A21512F326151743B2D841950960012E50C4C57620D9162459813D3C0CADAD15A228FD1E17E25F5F84BBAA062391886DCF9AA530CFD51C5204631FFBEEF494281086D8AD2A18BD741EBE3C2FDC64AA0DF643A221E9E64ACEF4912D575C1B6E9160B2CD38313E06A9AB0B6A6F37DCC129F08D304D177AA189DD303E8E71AAA669415A28CA00CBB2EC17C0B06CB85D6EC82306B4B202A8AACC3097421A1E04BA3A81B27220975C342ECB865B809A26901C05FAFB2045235002A55068CF95E3A19F7148AA2AC9DC82A9B131510B228B3E01ACE87A5A4EA7D2E4AF42A3E10C2932874B83E47601DE1C4E23B3A108309E043C1EAE9AE83926C39FA6D3340A11EEFC02CE19BF921683EF5CE966A4244D439A5B51D775F12913EA7432393AA21B3A14970B1E367D4C2D816EA4E1B0E18B6FE0DDF1B8338B19862BE186384728A2F78B08787D70B89540A68AA805D3869EA09394BCE28033EEF6401C340C373D842E0E85F1E4C848BFCEA6609385D75F0CD7FC6A240686611150F532F4642D9CC830F779196EE65A84DC4715CFEC5AF07A388711A2A36A8E1B161D4D7E3D0075F122F84A4AE0D061B26595C72F13339101BEDCDF7F26C156A85BB6ACB2F917D5FC000C2A5264E01411B088A1156C04380D7E4B0994195339A780CF7E3752AC1561A3E88EBBE06614D396258D311D9148A48B9F9302180D0D0DAD43434349DD34A434B7E2B485B7831D1903E118F47C329A9207A990BD895E3B1EB2CB30CDB26588DD5E38CC9A54A0314DB9D00BBC88C4C730441BD3EEB88371959DB461C883D1A8190A855AF8D9CA00B7B6B686C2E17033B385CBD141BB2818C49C17B621F4C51944D39C52C85D5DC18A2EE4B39580ADA7609B06D584CD5A8049375953985D4AB67EC44C0DA1503BA63FF51B94CC999BB1C9510C0E0D9D3C74E8900046F6384C7A3C9EDCA953A7AE4EA752725E418155B960817C2E16C5C03BFB50B178215C736E8233730E241A96DC8239C32AF25FC888D0517BEE5CC84C53BA2F860F5FFB0786EF5989BA273723924CDA5D5F9D550C16DCB1A6A6D7BFE8ECFC80B876F65747909DF5D8E38FBF31BFF2E6BA5472D4ACABAB53F25445FAD78B2FA2FC9D7F62CDAB4FC153BB06983E178E447F5928574E071694C32ABEF4258CA34770F88997F1D59AD5B8EFA597606A2EE7E0E1C396CBE351BF3C7BB671C78E1D8F10A7937B39DB05F80A88B0AF7DF6D9675F2F2F2D9DEA5896C1DF16B5C0E7951ADF7F1F78F9712C7CE06728FBE906C89535AC66F601B123933CB9BE0821B2EF6DB4BCF977A49EFB0396D6D763DCB69D03FF3960F207518BC66283DBB76F7F8CB37753D96D2676E4C45DB0CE2D2929D9B069D3A617FC858579A6AE9BF7D4D5C9336706E5C1DECB881FFF1CBEAF4EB0B6DC700598738A11EDC3280B2939BB0A79772E41C9B469F8BAB7D73E72F830A11D75647434BD67CF9E6D9D9D9D6F70FA3055B89BF901E33D2322ECEC8452607A30F8CBF5F5F59B037EFF24EE715455579BB75655C9054545926C1892941881ACB3A828B6C6E6929707DBE57212FC053A7DAAC36E6B6D5173B8CF633C9A0E1C3CF85A477BFB4EDA1EC81CA957BA7A063D63207BC9C45F92C489B6EEE1871F7EB4BCBC7C91A85E95C7E18C1933ECF2E9D39DC24040D2B84F8598EC78F1C141A7EFD225A9A7BB5BD6D99D44B71B08874FBEB573E75B9CB28F36C3041511BD2A82F6B764029CE58A9A65CBEEFEF12DF32B57F17FF926168122CE5C37FBAEF8FF1262B33FEBFCE5B1582E2C193B954E9FFBB2ABEBC8471F7DB48FC32DB495F826A858F79DC0624088F811DCBD7B77091F6F5B70FBED8B2A66CDAAF6FA7C3369C8AF2A8A573868D9F618A9C478F27453DA8F1D3BD6C4F9A1E5CB970F1C3D7A345348C2D637E586C0D74C16F4C471269C6097803F273FDF47D6D2682CC6F31031EA00354C8D53B9BF6E2CFF2FF0B556C41AE14876ADC89D00BA2E877CBFA1FC0FAA5776E8A6DB6A9D0000000049454E44AE426082";
    private String hex_greenLight = "89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA2000000017352474200AECE1CE9000000097048597300000B1300000B1301009A9C180000015969545874584D4C3A636F6D2E61646F62652E786D7000000000003C783A786D706D65746120786D6C6E733A783D2261646F62653A6E733A6D6574612F2220783A786D70746B3D22584D5020436F726520352E342E30223E0A2020203C7264663A52444620786D6C6E733A7264663D22687474703A2F2F7777772E77332E6F72672F313939392F30322F32322D7264662D73796E7461782D6E7323223E0A2020202020203C7264663A4465736372697074696F6E207264663A61626F75743D22220A202020202020202020202020786D6C6E733A746966663D22687474703A2F2F6E732E61646F62652E636F6D2F746966662F312E302F223E0A2020202020202020203C746966663A4F7269656E746174696F6E3E313C2F746966663A4F7269656E746174696F6E3E0A2020202020203C2F7264663A4465736372697074696F6E3E0A2020203C2F7264663A5244463E0A3C2F783A786D706D6574613E0A4CC227590000098649444154480D8D577B7054D519FFDDC7DEDD64B39B4DB22421C1BC0489400388A156C5528A384389A3526C9DFEA31247AB23ADCDB433D0A9856AA7A3334C5B47C7EA743A237F749480A3B536C1115126268D21C086248A9001F2CE66DFEFBBF7DE73FA9D9B2C45AB4E4FE6EC3DAFEFFB7DEF7322E16B1AE75C9124C9BA66FB86A3478FDE5A5B5BBBDEEBF52E57557509ED4BA665869289E4D8CCCC4CE0E9679FEE0D0C04460A348B3C18CD7961ED9BBE9220281CE8E8E8B8BDBFBFFF35623C6759166D7D7D9B9D9D0D0F0E0EBEBE7FFFFEAD057A3A2DD3582ACC0BDF2F2F0850A18890D27FECD8B15FB7B4B4FCB4BABADA6918062E5DBA84B1CB17D9F8E4254413115B0D5F4919EA6AEBD1D4B85C6E6A6C82D3E9442814B2CE9D3BF7B72D5BB61C203E53029C780AADBF5273012AA4136DD5C0E0402FCD79369BE5277B3E32F63FB7D76CDE090B5BC1FC3F02FB767B09BBA5BD942D7D004CACD5DD036BEFB31DE6F113EF1BC9644A90F240E04C60FBF6ED370B8634154A5E55F4EA80360A3E6D3E73E6CCD175EBD6AD1A1B1B33DFEE3A82C3FDCF2BFD4644DA73D343F8CE9AEFA2AEA6011EB7171251A7D2494CCC8EE393918FF1D7C1BFA09E83FFB8F577EC9EEDBB58F3CA66C7F0F0F095F6F6F69DE4AE41C2F8A2E602544845ADB2B7B7B787E67C70F054FEE7BF79CCBAE51137DFFAD42AFE46F7213E19BCC4334682EB3CCD75B6D8699C35137C3A3CCEDFFAE00DBEE3971BF9CDEDE08FEF7D90F5F5F5EA82D7C0C0C010F16E1000345DC0DAB56B57011414B52FE8BACE034301F3A9DF3E66DDDDB191B73C01DEF9C1211ED7E779D288F050668ACFA727F97C66B1D358AC258DB0DDFFF97127DFF83327DFF18B0DFC897D0FB253A480699AFC9D77DE3944B8B6850F1F3EAC28232323D2810307389963CB8E1D3BFEC81857DEEE3ECCAEA446957F474FE0FE5B1EC7F63BDAC0650BC95C184C36614906393BBFD0A53C0CAE23958BC3E406FCFE0A187917FE7EFE081A3DB5522C98447D4D93ECF3F9D6523F7BF2E4C9CF0818C2E62282A5B6B6B6876B6A6AB4B3E74E1B97A39FAA092B865C19D0BCF20618721AB1FC2C7439890C8F518F5EED697B1C83AE241133E6A02381152BAEC7D22A2062043199B8A00C9E1D30962C5982DB6EBFAD9DB09C22C20B51DC52595979D7FCFC3CCE5F1A96753983A07516EB1B00CB95458A859046040224CD2234A7F135DF340BD31EADD199148D2D2D8D950D5ECCF201184A0E17264695E9E969F82BFC9BEFBBFF3E3BCA6DE0E70F3E7FABA7C4E39F981C07F94ACEB214B4D22C3C150AA2984082CDDBA049629A14805604492BBAF8A531AD25AD305921629F0DF371387D3A4ACB6512248E0C8B4B1353E3DCE3F1BAB76EDE7A07690D55FC54F9ABD6CB8A82702C64491A9793A4A1D757056F4909493D8A6AA312A572392C66916F84AC2246EC38A12FE58FF8E30C8AA490E0314C5B237097D4C22CB3109B9C81A4B520129B670DD7352AE4E77544A4086037D5DD3A3D97432E9F81EA5091E5F3287717C3EDF2C1525218CF0FA3565901A75C4C3805C0C2770158CC742B8B69FD22F24A8468CB912B4E90F12788A7829C9143269B81C3E168A0A36536B02CCBE5D95CD6965AD3348ADA0434C732124B854B2DA2804962267F115EA5D206572495F4950BBA8271133ACB20610591458C684AC9B719689A9378A5C84AAA04AA985405451E7B6D65E947C9E7F332E52F14324091EA16A14E5D21D3A950650D9A5444C7A84AB128744E56919CF6BE303323138B743278CE16C429BBC1A41CD11A02D0A62B7216C3A13A4038D0F379611CDBD47A2A954A187983363578351F5C72352CC382020DAA429DC00598AD2909242ABD00144D187AC1022ECA6B957259273E442B3960E52DB25005CABD155C339D94DF067259B23790172265097856682C91FF7C2515A82EBE01E1543F2456075575DAC00A77D85F190E5B5BB29DC02558669BDA8249139D0A0D271A46BCF2C8A4E3A874B5A0CCE3473ECA9137F288C7E3D3449614219AA51CFB2C994AC2CC33D9AD79B0A2B205B3A139E2A390D9346226B475D81650682EFA820516C6B2BD46FB74068AF0AB66EB3417BA82E6CA0DF0169591B6A694C96440B5E23C61A6EC3CEEE9E9198C442229BA732533C7F8B71A3740CD02F1F918B8418C143731139AAAE46F97ED73875C048DA2DC41FE176B0A99D9A27D93D618993515CA201303D636B5829BA47FCE9043E1901908044E11B06503D3AB21100C060738396F7E36C496F9EBB1B3E5F7E819EA81196528964BA1A99530887D8EE2D624338A9CB618D56D6650C0E5A05340AA8A1F454A29784242DFD009DCDBBC178DD52B109C09D901110947CE7677770B60E2B4D0522E97AB64D9B265DB73B99C5CEAF15AEBD66C908363710C4E1EC28DCB6F47ADEF7A5448F5142CE21E5629998405C8BCA479895A830AB501EE220FD2C9283E78FFCF68C8FD00BBEFDE837828C93E3F7F41314D0BFD7DFD2F8D7E3AFA2F826485A78E08D4A63D4FEE7979D5EA1BB765721973DBB63B15CDAD4A2FBEFE1C3EF3BE8607DAFE80D6EAEFA3466AA4A093297DF22227C9D70EFBE69AC1159C9EFF089D5DBF42D5D4BDE8F8C9012896837777755B4ECDA55EB870B1F7E0C1838F10CE28D18937C4D526CCBE63DFBE7D2F5555572D63DC32E8D9A29694BAA5AE8FFE81E3C9177153EB366C69F8219697AC861B1E3A2E2143C5652C3D8213E36FE293536F6193FA38DABEB713A6CEF8BBEFBE6B9A86E98884A3E1679E79E64922E8A44EE1FFDF822B04105A97D0F5F5F0A38F3EFAACAFCCE731CDBC79E79DDBE486C646792E348DE1E000A6D4F370B98B50EAF493C925C4F4796452292C355660CD928DA8ADBC0E539353ECD87BC79830482A91D28F1C3972607474F465E24FE166E7E1D5C24B734A3D3A49CCFC0D0D0D0FD1CBA4A3BCA2BC52BCA9D6B6AC355B5AD6CA65A5659225515AB024059A2E68289635B8258F48251E4FC4F9F0C8303B7D7A502D2E72231E8BC7BABABAFE343434F40AF19E138252130A2E5601315A6CB6FD25A982A66DBB77EF7E6CE9D2A51B4D8A5E4595515F57CFAEABADE3153EBFA4392857A919A641374F984FCE4C48972F5F960B15706E2E78F6D5575F79958EBC493C83E2F2B709167FAEF5F1D5F54570372DACDFB469D3BDAB57AFB9CBE572AEA41B5111A23A3495EAFA4242588C6A3595425141A9F2B15C4E1FFBFCF3F3EF1D3F7EFC4DA23F45BC925F0615405F092C3644130FC1CECECE25346C696D6DDDD8D4D4B4CE5DECA6B096CA55552916023246D7124394328114BE3CD4D7D7D74FE7039B376F9EFBF0C30FED4012BCBEDCBE11F89AC3423D719D0921E83545D77591D7ADC88A944847D3348F529FA31EA41EA77EEDFF5C34FDDFF6FF025F4B29688420055AE13B01F4051FD2FC1BDB7F00929F11C14DFD26ED0000000049454E44AE426082";
    private JLabel lb_settings = new JLabel(new ImageIcon("settings.png"));
    private JLabel lb_connectIndicator = new JLabel(new ImageIcon(DatatypeConverter.parseHexBinary(this.hex_redLight)));
    private JLabel lb_uploadStatus = new JLabel(" ");
    private JLabel lb_loginStatus = new JLabel(" ", JLabel.RIGHT);
    
    private JTextField tf_dozent = new JTextField();
    private JTextField tf_titel = new JTextField();
    private JTextField tf_beschreibung = new JTextField();
    
    private JProgressBar pb_progress = new JProgressBar();
    
    
    private JPanel connectIndicator = new JPanel();
    
    private File movie;
    private boolean upload;
    
    public Window() {
        this.window = new JFrame("Flowcast Media Uploader");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setMinimumSize(new Dimension(400,350));
        this.window.setLocation(300, 150);
        this.window.setLayout(new BorderLayout());
        this.window.setResizable(false);
        
        this.server = new Server();
        this.settings = new Settings(window);
        this.ftp = new Ftp(this);
        
        this.upload = false;
        this.testConnection();
        
        lb_serverIP.setFont(new Font(lb_serverIP.getFont().getName(), Font.PLAIN, 15));
        lb_username.setFont(new Font(lb_serverIP.getFont().getName(), Font.PLAIN, 15));
        lb_dozent.setFont(new Font(lb_dozent.getFont().getName(), Font.PLAIN, 14));
        lb_titel.setFont(new Font(lb_titel.getFont().getName(), Font.PLAIN, 14));
        lb_beschreibung.setFont(new Font(lb_beschreibung.getFont().getName(), Font.PLAIN, 15));
        lb_workflows.setFont(new Font(lb_workflows.getFont().getName(), Font.PLAIN, 14));
        lb_uploadStatus.setFont(new Font(lb_uploadStatus.getFont().getName(), Font.PLAIN, 11));
        lb_loginStatus.setFont(new Font(lb_loginStatus.getFont().getName(), Font.PLAIN, 11));
        
        this.b_upload.setEnabled(this.enableUpload());
        
        JPanel panel = new JPanel();
        SpringLayout springPanel = new SpringLayout();
        panel.setLayout(springPanel);
        this.pb_progress.setStringPainted(true);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_connectIndicator, 10, SpringLayout.NORTH, panel);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_connectIndicator, -10, SpringLayout.EAST, panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_connectIndicator, 30, SpringLayout.NORTH, this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_connectIndicator, -30, SpringLayout.EAST, this.lb_connectIndicator);
        panel.add(this.lb_connectIndicator);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_connect, 13, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_connect, -5, SpringLayout.WEST , this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_connect, 25, SpringLayout.NORTH , this.b_connect);
        springPanel.putConstraint(SpringLayout.WEST, this.b_connect, -100, SpringLayout.EAST , this.b_connect);
        panel.add(this.b_connect);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_serverIP, 10, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_serverIP, 10, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_serverIP, 30, SpringLayout.NORTH , panel);
        panel.add(this.lb_serverIP);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_username, 0, SpringLayout.SOUTH , this.lb_serverIP);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_username, 10, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_username, 20, SpringLayout.NORTH , this.lb_username);
        panel.add(this.lb_username);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_loginStatus, 5, SpringLayout.SOUTH , this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_loginStatus, 0, SpringLayout.EAST , this.lb_connectIndicator);
        //springPanel.putConstraint(SpringLayout.SOUTH, this.lb_loginStatus, 25, SpringLayout.NORTH , this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_loginStatus, -300, SpringLayout.EAST , this.lb_connectIndicator);
        panel.add(this.lb_loginStatus);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_fileChooser, 15, SpringLayout.SOUTH , this.lb_username);
        springPanel.putConstraint(SpringLayout.WEST, this.b_fileChooser, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_fileChooser, 25, SpringLayout.NORTH , this.b_fileChooser);
        panel.add(this.b_fileChooser);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_datei, -1, SpringLayout.NORTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_datei, -9, SpringLayout.EAST , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_datei, 5, SpringLayout.EAST , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_datei, 27, SpringLayout.NORTH , this.lb_datei);
        panel.add(this.lb_datei);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_dozent, 3, SpringLayout.SOUTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_dozent, 7, SpringLayout.WEST , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_dozent, 60, SpringLayout.WEST , this.lb_dozent);
        panel.add(this.lb_dozent);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_titel, 12, SpringLayout.SOUTH , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_titel, 7, SpringLayout.WEST , b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_titel, 60, SpringLayout.WEST , this.lb_titel);
        panel.add(this.lb_titel);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_dozent, 1, SpringLayout.SOUTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_dozent, 0, SpringLayout.EAST , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_dozent, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_dozent);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_titel, 1, SpringLayout.SOUTH , this.tf_dozent);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_titel, 0, SpringLayout.EAST , this.lb_titel);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_titel, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_titel);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_beschreibung, 20, SpringLayout.SOUTH , this.lb_titel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_beschreibung, 0, SpringLayout.WEST , this.lb_dozent);
        panel.add(this.lb_beschreibung);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_beschreibung, 2, SpringLayout.SOUTH , this.lb_beschreibung);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_beschreibung, 8, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_beschreibung, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_beschreibung);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_workflows, 4, SpringLayout.SOUTH , this.tf_beschreibung);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_workflows, 0, SpringLayout.WEST , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_workflows, 70, SpringLayout.WEST , this.lb_workflows);
        panel.add(this.lb_workflows);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.cb_workflows, -4, SpringLayout.NORTH , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.WEST, this.cb_workflows, 0, SpringLayout.EAST , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.EAST, this.cb_workflows, -6, SpringLayout.EAST , panel);
        panel.add(this.cb_workflows);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_upload, 8, SpringLayout.SOUTH , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.WEST, this.b_upload, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_upload, 83, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_upload, 32, SpringLayout.NORTH , this.b_upload);
        panel.add(this.b_upload);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_uploadStatus, 2, SpringLayout.NORTH , this.b_upload);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_uploadStatus, 3, SpringLayout.EAST , this.b_upload);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_uploadStatus, -9, SpringLayout.EAST , panel);
        panel.add(this.lb_uploadStatus);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.pb_progress, -4, SpringLayout.SOUTH , this.lb_uploadStatus);
        springPanel.putConstraint(SpringLayout.WEST, this.pb_progress, 0, SpringLayout.EAST , this.b_upload);
        springPanel.putConstraint(SpringLayout.EAST, this.pb_progress, -9, SpringLayout.EAST , panel);
        //springPanel.putConstraint(SpringLayout.SOUTH, this.pb_progress, 13, SpringLayout.NORTH , this.pb_progress);
        panel.add(this.pb_progress);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_test, 5, SpringLayout.SOUTH , this.b_upload);
        springPanel.putConstraint(SpringLayout.WEST, this.b_test, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_test, 83, SpringLayout.WEST , panel);
        panel.add(this.b_test);
   
        window.add(panel, BorderLayout.CENTER);
        
        this.b_fileChooser.addActionListener(this);
        this.b_connect.addActionListener(this);
        this.b_upload.addActionListener(this);
        this.b_test.addActionListener(this);
        this.cb_workflows.addActionListener(this);
        
        this.tf_dozent.getDocument().addDocumentListener(this);
        this.tf_titel.getDocument().addDocumentListener(this);        
        this.tf_beschreibung.getDocument().addDocumentListener(this);
        
        this.lb_settings.addMouseListener(this);
        
        window.setVisible(true);
    }
    
    public void testConnection(){
        new Thread( new Runnable(){
            @Override 
            public void run(){
                while (true) {
                    if (ftp.Connected() && !upload && !ftp.testConnection()) {
                        lb_connectIndicator.setIcon(new ImageIcon(DatatypeConverter.parseHexBinary(hex_redLight)));
                        b_connect.setText("Connect");
                        b_upload.setEnabled(false);
                    }
                    
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        } ).start();
    }
    
    public boolean enableUpload(){
        boolean datei = this.lb_datei.getText().length() > 0;
        boolean dozent = this.tf_dozent.getText().length() > 0;
        boolean titel = this.tf_titel.getText().length() > 0;
        boolean beschreibung = this.tf_beschreibung.getText().length() > 0;
        boolean workflow = this.cb_workflows.getSelectedItem().toString().length() > 0;
        
        return this.ftp.Connected() && datei && dozent && titel && beschreibung && workflow;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == this.b_connect) {
            
            if(this.ftp.Connected()){
                if (this.ftp.logOut()) {
                    lb_connectIndicator.setIcon(new ImageIcon(DatatypeConverter.parseHexBinary(this.hex_redLight)));
                    b_connect.setText("Connect");
                    b_upload.setEnabled(enableUpload());
                }
            }
            else {
                this.settings.setLocation((int) (window.getLocation().getX() + ((window.getSize().width - this.settings.getSize().width) / 2)), (int) (window.getLocation().getY() + 72));
                this.settings.setVisible(true);
                char[] pass = this.settings.getPassword();
                if (pass == null) {
                    return;
                }
                this.server.key(pass);
                Arrays.fill(pass, '0');
            
                if (this.ftp.logIn(this.server)) {
                    this.lb_connectIndicator.setIcon(new ImageIcon(DatatypeConverter.parseHexBinary(this.hex_greenLight)));
                    this.b_connect.setText("Disconnect");
                    this.lb_loginStatus.setText(" ");
                    this.b_upload.setEnabled(this.enableUpload());
                }
            }
            
            this.b_upload.setEnabled(this.enableUpload());
        }
        else if(o == this.b_fileChooser){
            String pfad = null;
            if ("Mac OS X".equals(System.getProperty("os.name"))) {
                pfad = "/Users/" + System.getProperty("user.name") + "/Movies/";
            } else if (System.getProperty("os.name").startsWith("Windows")) {
                pfad = "C:\\Users\\" + System.getProperty("user.name") + "\\Videos";
            }
            
            JFileChooser chooser = new JFileChooser(pfad);
            FileFilter filter = new FileNameExtensionFilter("Videodatei", "mp4", "mov", "m4v");
            chooser.setFileFilter(filter);
            int open = chooser.showOpenDialog(null);
            if (open == JFileChooser.APPROVE_OPTION){
		this.movie = chooser.getSelectedFile();
                this.lb_datei.setText(this.movie.getName());
                this.b_upload.setEnabled(this.enableUpload());
                this.lb_uploadStatus.setText(" ");
                this.pb_progress.setValue(0);
            }
        }
        else if (o == this.b_upload) {
            this.b_connect.setEnabled(false);
            this.b_upload.setEnabled(false);
            this.b_fileChooser.setEnabled(false);
            if(this.ftp.Connected()){
                this.upload = true;
                this.ftp.upload(this.movie, this.tf_dozent.getText(), this.tf_titel.getText(), this.tf_beschreibung.getText(), this.cb_workflows.getSelectedItem().toString());
            }
            else {
                this.lb_uploadStatus.setText("Connection lost!");
            }
            
        }
        else if (o == this.cb_workflows) {
            this.b_upload.setEnabled(this.enableUpload());
        }
        else if (o == this.b_test) {
            try {
                
                
                byte[] bytes2 = DatatypeConverter.parseHexBinary(this.hex_redLight);
                System.out.println(Arrays.toString(bytes2));
                System.out.println(bytes2.length);
                
                InputStream in = new ByteArrayInputStream(bytes2);
                BufferedImage image = ImageIO.read(in);
                System.out.println(image.toString());
                this.lb_connectIndicator.setIcon(new ImageIcon(bytes2));
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.b_upload.setEnabled(this.enableUpload());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.b_upload.setEnabled(this.enableUpload());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }

    public JButton getBUpload(){
        return this.b_upload;
    }
    
    public JButton getBConnect(){
        return this.b_connect;
    }
    
    public JButton getBFileChooser(){
        return this.b_fileChooser;
    }
    
    public JProgressBar getPBProgress(){
        return this.pb_progress;
    }
    
    public void setLBUploadStatus(String text){
        this.lb_uploadStatus.setText(text);
    }
    
    public void setLBLoginStatus(String text){
        this.lb_loginStatus.setText(text);
    }
    
    public void setUpload(boolean bo){
        this.upload = bo;
    }
    
    public JLabel getLBIndicator(){
        return this.lb_connectIndicator;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == this.lb_settings) {
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
    
