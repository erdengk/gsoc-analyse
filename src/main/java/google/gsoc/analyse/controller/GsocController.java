package google.gsoc.analyse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import google.gsoc.analyse.mapper.AnalyseMapper;
import google.gsoc.analyse.mapper.GsocMapper;
import google.gsoc.analyse.model.Analyse;
import google.gsoc.analyse.model.Gsoc;
import google.gsoc.analyse.result.Result;
import google.gsoc.analyse.service.AnalyseService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class GsocController {

    @Autowired
    private GsocMapper gsocMapper;
    @Autowired
    private AnalyseService analyseService;


    @ResponseBody
    @GetMapping("getGsocById")
    public Result getGsocById( @RequestParam Integer id ) {
        Gsoc gsoc = gsocMapper.selectById( id );
        return Result.ok( gsoc );
    }


    @ResponseBody
    @GetMapping("generateRankingByYear")
    public Result generateRanking( @RequestParam Integer year ) {

        QueryWrapper<Gsoc> gsocQueryWrapper = new QueryWrapper<>();
        gsocQueryWrapper.eq( "year", year );
        List<Gsoc> gsocList = gsocMapper.selectList( gsocQueryWrapper );

        for ( Gsoc gsoc : gsocList ) {
            gsoc.setTechnologies( gsoc.getTechnologies().replace( " ", "" ) );

            String[] technologies = gsoc.getTechnologies().split( "," );
            for ( int i = 0; i < technologies.length; i++ ) {
                QueryWrapper<Analyse> analyseQueryWrapper = new QueryWrapper<>();
                analyseQueryWrapper.eq( "technologies", technologies[i] );

                Analyse analyse = analyseService.getOne( analyseQueryWrapper );
                if ( analyse == null ) {
                    analyse = new Analyse();
                    analyse.setYear( year );
                    analyse.setTechnologies( technologies[i] );
                    analyse.setParticipation( 1 );
                    if ( gsoc.getTechnologies().contains( analyse.getTechnologies() ) ) {
                        analyse.setOrganization( gsoc.getName() + "," );
                    }
                    analyseService.save( analyse );
                } else {
                    analyse.setParticipation( analyse.getParticipation() + 1 );
                    if ( gsoc.getTechnologies().contains( analyse.getTechnologies() ) ) {
                        analyse.setOrganization( analyse.getOrganization() + gsoc.getName() + "," );
                    }
                    analyseService.updateById( analyse );
                }
            }

        }

        QueryWrapper<Analyse> analyseQueryWrapper = new QueryWrapper<>();
        analyseQueryWrapper.eq( "year", year ).orderByDesc( "participation" );
        List<Analyse> analyseList = analyseService.list( analyseQueryWrapper );

        int num = 1;
        for ( Analyse anlyse : analyseList ) {
            anlyse.setRanking( num++ );
            analyseService.updateById( anlyse );
        }
        return Result.ok();
    }

    @ResponseBody
    @GetMapping("generateRightRanking")
    public Result generateRightRanking( @RequestParam Integer year ) {

        QueryWrapper<Analyse> analyseQueryWrapper = new QueryWrapper<>();
        analyseQueryWrapper.eq( "year", year ).orderByDesc( "participation" );
        List<Analyse> analyseList = analyseService.list( analyseQueryWrapper );

        int num = 1;
        for ( Analyse anlyse : analyseList ) {
            anlyse.setRanking( num++ );
            analyseService.updateById( anlyse );
        }
        return Result.ok();

    }

}

