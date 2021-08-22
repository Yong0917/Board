package yong.board.mapper;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import yong.board.vo.MemberVo;

import java.util.List;

@Repository
@Mapper
public interface RegisterMapper {

    List<MemberVo> checkMember(MemberVo memberVo);

    void joinMember(MemberVo memberVo);

    MemberVo selectMember(String username);

    String selectQrCord(MemberVo memberVo);

    String selectSecretKey(MemberVo memberVo);
}
