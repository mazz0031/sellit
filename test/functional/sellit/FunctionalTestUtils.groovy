package sellit

/**
 * Created by mark.mazzitello on 3/27/2015.
 */


class FunctionalTestUtils {

    Integer songId
    Integer radioheadId

    @Delegate
    HttpUtils httpUtils = new HttpUtils()

    def setupSampleData() {
        def response = httpUtils.doFormPost('j_spring_security_check', [j_username: 'Admin', j_password: 'password1'])
        assert response.status == 302
        assert response.statusText == 'Found'

        def remote = new SellitRemoteControl()
//        songId = remote {
//            def radiohead = new Artist(name: 'Radiohead')
//            radiohead.save(flush: true)
//            def song = new Song(title: 'Creep', artist: radiohead)
//            song.save(flush: true)
//            println "Song id ${song.id}"
//            return song.id
//        } as Integer

        radioheadId = remote {
            Artist.findByName('Radiohead').id
        } as Integer
    }

    def cleanupSampleData() {
        def remote = new SellitRemoteControl()
//        remote {
//            Song.withTransaction {
//                def radiohead = Artist.findByName('Radiohead')
//                Song.findAllWhere([artist: radiohead]).each { song ->
//                    Play.findAllWhere([song: song]).each { it.delete() }
//                    song.delete()
//                }
//                Follow.findAllWhere([artist: radiohead]).each {
//                    it.delete()
//                }
//                radiohead.delete()
//            }
//        }
    }
}