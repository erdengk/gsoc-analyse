package google.gsoc.analyse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import google.gsoc.analyse.model.Analyse;
import google.gsoc.analyse.model.Gsoc;
import google.gsoc.analyse.result.Result;
import google.gsoc.analyse.service.AnalyseService;
import google.gsoc.analyse.service.GsocService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AnalyseController {

    @Autowired
    private AnalyseService analyseService;
    @Autowired
    private GsocService gsocService;


    @ResponseBody
    @GetMapping("getRankingByYear")
    public Result getRankingByYear( @RequestParam Integer year ) {
        QueryWrapper<Analyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "year", year ).orderByDesc( "participation" ).last( "limit 0,10" );
        List<Analyse> list = analyseService.list( queryWrapper );
        return Result.ok( list );
    }


    @ResponseBody
    @GetMapping("generateAnalyseByYear")
    public Result generateAnalyseByYear( @RequestParam Integer year ) {
        QueryWrapper<Analyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "year", year ).orderByDesc( "participation" );
        List<Analyse> analyseList = analyseService.list( queryWrapper );

        List<String> result = new ArrayList<>();
        for ( Analyse analyse : analyseList ) {
            String introduction = "## Number "+analyse.getRanking() +" "+ analyse.getTechnologies() + "（" + analyse.getParticipation() +  "times )" ;
            String[] organizations = analyse.getOrganization().split( "," );
            for ( int i = 0; i < organizations.length; i++ ) {
                QueryWrapper<Gsoc> gsocQueryWrapper = new QueryWrapper<>();
                gsocQueryWrapper.eq( "year", year ).eq( "name", organizations[i] );
                Gsoc gsoc = gsocService.getOne( gsocQueryWrapper );
                if ( gsoc!=null ){
                    introduction = introduction + " [" + gsoc.getName() + "]" + "(" + gsoc.getGsocUrl() + ")" + "<br>";
                }

            }
            result.add( "<br>" );
            result.add( introduction );
        }

        return Result.ok( result );
    }


    @ResponseBody
    @GetMapping("generateAnalyseAndImgByYear")
    public Result generateAnalyseAndImgByYear( @RequestParam Integer year ) {
        QueryWrapper<Analyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "year", year ).eq( "ranking",1 ).orderByDesc( "participation" );
        List<Analyse> analyseList = analyseService.list( queryWrapper );

        List<String> result = new ArrayList<>();
        for ( Analyse analyse : analyseList ) {
            String introduction = "## Number "+analyse.getRanking() +" "+ analyse.getTechnologies();
            String[] organizations = analyse.getOrganization().split( "," );
            for ( int i = 0; i < organizations.length; i++ ) {
                QueryWrapper<Gsoc> gsocQueryWrapper = new QueryWrapper<>();
                gsocQueryWrapper.eq( "year", year ).eq( "name", organizations[i] );
                Gsoc gsoc = gsocService.getOne( gsocQueryWrapper );
                if ( gsoc!=null ){
                    introduction = introduction + "![" + gsoc.getName() + "]" + "(" + gsoc.getLogImg() + ")" +
                            "<br>";
                }
            }
            result.add( "<br>" );
            result.add( introduction );
        }

        return Result.ok( result );
    }


}



